/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;


/**
 *
 * @author kpaul
 * @param <T> entity, which can be used for making pagination
 */
public final class Page<T>{

    private final String query;
    private final SessionFactory sessionFactory;
    private int resultListCount;
    private int pageSize = 3;
    private int pageCount;
    private boolean paginated;
    private String[] projection;
    private String sortType ="Asc";
    private Class<T> clazz;


    public Page(SessionFactory sessionFactory,String query,String[] projection) {
        this.sessionFactory = sessionFactory;
        this.query =query;
        this.projection = projection;
        this.clazz = (Class<T>) getClass().getTypeParameters()[0].getGenericDeclaration();
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public int getResultListCount() {
        return resultListCount;
    }


    public Integer getPageCount() {
        return pageCount;
    }

    public List<Hospital> getHospitalPageList(Integer page) {
        FullTextSession session = Search.getFullTextSession(this.sessionFactory.openSession());
        Order order = this.sortType.startsWith("Ascen")?Order.asc("name"):Order.desc("name");

        QueryBuilder builder = session.getSearchFactory().buildQueryBuilder().forEntity(Hospital.class).get();

        Query query = builder.phrase().onField(projection[0])
                .andField(projection[1])
                .andField(projection[2])
                .andField(projection[3])
                .andField(projection[4])
                .andField(projection[5])
                .sentence(this.query.toLowerCase()).createQuery();

        FullTextQuery fullTextQuery = session.createFullTextQuery(query, Hospital.class).setCriteriaQuery(session.createCriteria(Hospital.class).addOrder(order));

        this.resultListCount = fullTextQuery.getResultSize();
        calcPageCount();

        fullTextQuery.setFirstResult((pageSize*(page-1))).setMaxResults(pageSize);
        fullTextQuery.setSort(new Sort(new SortField("name", Type.STRING_VAL)));
        List<Hospital> result = (List<Hospital>) fullTextQuery.list();
        session.close();
        return result;
    }

//    public List<DoctorSearchDTO> getDoctorPageList(Integer page) {
//        FullTextSession session = Search.getFullTextSession(this.sessionFactory.openSession());
//        Order order = this.sortType.startsWith("Ascen")?Order.asc("userDetails.firstName"):Order.desc("userDetails.firstName");
//
//        QueryBuilder builder = session.getSearchFactory().buildQueryBuilder().forEntity(DoctorInfo.class).get();
//
//        Query query = builder.phrase().onField(projection[0])
//                .andField(projection[1])
//                .andField(projection[2])
//                .sentence(this.query.toLowerCase()).createQuery();
//
//        FullTextQuery fullTextQuery = session.createFullTextQuery(query, DoctorInfo.class).setCriteriaQuery(session.createCriteria(UserDetail.class).addOrder(order));
//
//        this.resultListCount = fullTextQuery.getResultSize();
//        calcPageCount();
//
//        fullTextQuery.setFirstResult((pageSize*(page-1))).setMaxResults(pageSize);
//        fullTextQuery.setSort(new Sort(new SortField("userDetails.firstName", Type.STRING_VAL)));
//        List<DoctorInfo> result = (List<DoctorInfo>) fullTextQuery.list();
//        session.close();
//        List<DoctorSearchDTO> resultList = new ArrayList<>();
//        for(DoctorInfo docInfo: result){
//            List<Department> doctorDepartments = docInfo.getDepartments();
//            List<String> doctorHospitals = new ArrayList<>();
//            for (Department department: doctorDepartments){
//                doctorHospitals.add(department.getHospital().getName());
//            }
//
//            resultList.add(new DoctorSearchDTO(docInfo.getUserDetails().getImagePath(),
//                    docInfo.getUserDetails().getFirstName(),
//                    docInfo.getUserDetails().getLastName(),
//                    docInfo.getUserDetails().getUser().getEmail(),
//                    docInfo.getSpecialization(),
//                    docInfo.getCategory(),
//                    doctorHospitals));
//        }
//        return resultList;
//    }

    private void calcPageCount(){
        if (this.pageSize < this.resultListCount) {
            this.pageCount = this.resultListCount / this.pageSize;

            this.paginated = true;
        } else {
            this.pageCount = 1;
            this.paginated = false;
        }
    }

    public boolean isPaginated() {
        return paginated;
    }


    public String getSortType() {
        return sortType;
    }

}
