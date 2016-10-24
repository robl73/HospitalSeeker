package com.hospitalsearch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospitalsearch.controller.advice.HospitalControllerAdvice;
import com.hospitalsearch.controller.advice.HospitalControllerAdvice.FilterHospitalListEmptyException;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelLocalization;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Laboratory;
import com.hospitalsearch.entity.Language;
import com.hospitalsearch.entity.Test;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.DiagnosisPanelLocalizationService;
import com.hospitalsearch.service.DiagnosisPanelService;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.service.LaboratoryService;
import com.hospitalsearch.service.LanguageService;
import com.hospitalsearch.service.TestService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;

@Controller
public class HospitalController {

	@Autowired
	private LaboratoryService laboratoryService;

	@Autowired
	private DiagnosisPanelService diagnosisPanelService;

	@Autowired
	private DiagnosisPanelLocalizationService diagnosisPanelLocalizationService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private TestService testService;

	@Autowired
	private HospitalService service;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DoctorInfoService doctorInfoService;

	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String renderIndex(Map<String, Object> model) {
		return "layout";
	}

	@RequestMapping("/hospitals")
	public String renderHospitals(Map<String, Object> model, @RequestParam(value = "q", required = false) String query)
			throws ParseException, InterruptedException, FilterHospitalListEmptyException {
		Page pageableContent = null;
		if (query != null && !query.isEmpty()) {
			pageableContent = service.advancedHospitalSearch(query);
			pageableContent.setPageSize(3);
		}
		this.initializeModel(model, pageableContent, 1, query);
		if (pageableContent.getResultListCount() == 0) {
			throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Empty list");
		}
		return "paginatedLayout";
	}

	@RequestMapping(value = "/hospitals/filter", method = RequestMethod.POST)
	public String renderFilteredHospitalsByPage(@Valid @ModelAttribute("filter") HospitalFilterDTO dto,
			BindingResult results, Map<String, Object> model) throws Exception {

		List<Hospital> hospitals = service.filterHospitalsByAddress(dto);
		if (hospitals.isEmpty()) {
			throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Problem");
		} else {
			model.put("pagination", true);
			if (results.hasErrors()) {
				return "paginatedLayout";
			}
			return "paginatedLayout";
		}
	}

	@RequestMapping("/hospitals/page/{page}/params")
	public String renderHospitalsByPage(Map<String, Object> model, @PathVariable("page") Integer currentPage,
			@RequestParam("itemsPerPage") Integer pageSize,
			@RequestParam("currentSearchQuery") String currentSearchQuery) throws ParseException, InterruptedException {
		Page pageableContent = service.advancedHospitalSearch(currentSearchQuery);
		pageableContent.setPageSize(pageSize);
		initializeModel(model, pageableContent, currentPage, currentSearchQuery);
		return "paginatedLayout";
	}

	@RequestMapping("/hospital/{id}")
	public String renderDepartments(Map<String, Object> model, @PathVariable Long id) {
		Hospital hospital = service.getById(id);
		List<Department> lst = hospital.getDepartments();
		Laboratory laboratory = laboratoryService.getByHospital(service.getById(id));
		model.put("laboratory", laboratory);
		model.put("isLabory", "true");
		if (laboratory == null) {
			model.put("isLabory", "false");
		}
		model.put("departments", lst);
		model.put("hospital", service.getById(id));
		model.put("hid", id);
		return "departments";

	}

	@RequestMapping("/hospital/{hid}/laboratory/{id}")
	public String laboratory(Map<String, Object> model, @PathVariable Long hid, @PathVariable Long id, Locale locale) {

		Long languageId = (long) 1;
		for (Language language : languageService.getAll()) {
			if (locale.getLanguage().equals(language.getName())) {
				languageId = language.getId();
			}
		}
		Language language = languageService.getById(languageId);
		List<DiagnosisPanel> diagnosisPanels = diagnosisPanelService.getByLaboratory(laboratoryService.getById(id));
		List<DiagnosisPanelLocalization> diagnosisPanelLocalizations = new ArrayList<>();
		for (DiagnosisPanel diagnosisPanel : diagnosisPanels) {
			diagnosisPanelLocalizations
					.add(diagnosisPanelLocalizationService.getByDiagnosticPanelAndLanguage(diagnosisPanel, language));
		}
		model.put("diagnosisPanelLocalizations", diagnosisPanelLocalizations);
		model.put("hospital", service.getById(hid));
		model.put("laboratory", laboratoryService.getById(id));
		model.put("hid", hid);
		model.put("lid", id);
		return "laboratory";
	}

	@RequestMapping("/hospital/{hid}/laboratory/{lid}/diagnosisPanel/{id}")
	public String diagnosisPanelGet(Map<String, Object> model, @PathVariable Long hid, @PathVariable Long lid,
			@PathVariable Long id) {
		DiagnosisPanel diagnosisPanel = diagnosisPanelService.getById(id);
		List<Test> tests = testService.getByPanel(diagnosisPanel);
		Language language = languageService.getById(1);
		String panelName = diagnosisPanelLocalizationService.getByDiagnosticPanelAndLanguage(diagnosisPanel, language)
				.getName();
		model.put("tests", tests);
		model.put("hospital", service.getById(hid));
		model.put("laboratory", laboratoryService.getById(lid));
		model.put("diagnosisPanel", diagnosisPanelService.getById(id));
		model.put("diagnosisPanelLocalizations", "yes");
		model.put("panelName", panelName);
		model.put("hid", hid);
		model.put("lid", lid);
		model.put("dpid", id);
		return "diagnosisPanel";
	}

	@RequestMapping("/hospital/{hid}/department/{id}")
	public String renderDoctors(Map<String, Object> model, @PathVariable Long hid, @PathVariable Long id) {
		Department d = departmentService.getById(id);
		model.put("doctors", doctorInfoService.findByDepartmentId(id));
		model.put("department", d);
		model.put("hospital", d.getHospital());
		model.put("hid", hid);
		model.put("id", id);
		return "doctors";
	}

	public void initializeModel(Map<String, Object> model, Page pageableContent, Integer page, String query) {
		model.put("currentSearchQuery", query);
		model.put("pagedList", pageableContent.getPageList(page));
		model.put("pagination", pageableContent.isPaginated());
		model.put("pageCount", pageableContent.getPageCount());
		model.put("itemsPerPage", pageableContent.getPageSize());
		model.put("currentPage", page);
		model.put("itemsNumber", pageableContent.getResultListCount());
		model.put("type", "hospitals");
	}
}
