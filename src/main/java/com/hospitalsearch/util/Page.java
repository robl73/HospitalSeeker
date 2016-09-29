/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.util;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.hospitalsearch.entity.Hospital;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.PhraseMatchingContext;
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
    private int pageSize;
    private int pageCount;
    private boolean paginated;
    private String[] projection;
    private String sortType ="Asc";
    private Class<T> clazz;

    public Page(SessionFactory sessionFactory,String query,String[] projection, Class<T> type) {
        this.sessionFactory = sessionFactory;
        this.query =query;
        this.projection = projection;
        this.clazz=type;
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

    public List<T> getPageList(Integer page) {
        FullTextSession session = Search.getFullTextSession(this.sessionFactory.openSession());

        QueryBuilder builder = session.getSearchFactory().buildQueryBuilder().forEntity(this.clazz).get();
        PhraseMatchingContext phraseContext = builder.phrase().onField(projection[0]);
        int count = 1;
        while (count < projection.length) {
            phraseContext = phraseContext.andField(projection[count]);
            count++;
        }
        Query query = phraseContext.sentence(this.query.toLowerCase()).createQuery();

        FullTextQuery fullTextQuery = session.createFullTextQuery(query, this.clazz).setCriteriaQuery(session.createCriteria(this.clazz));
        this.resultListCount = fullTextQuery.getResultSize();
        calcPageCount();
        fullTextQuery.setFirstResult((pageSize*(page-1))).setMaxResults(pageSize);
       List<T> result = (List<T>) fullTextQuery.list();
        session.close();
        return result;
    }

    private void calcPageCount(){
        if (this.pageSize < this.resultListCount) {
            this.pageCount = (int) Math.ceil(this.resultListCount/ (this.pageSize * 1.0));
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
