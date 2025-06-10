package com.example.testutils;

import org.hibernate.*;
import org.hibernate.Query;
import org.hibernate.graph.RootGraph;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.NativeQuery;
import org.hibernate.stat.SessionStatistics;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;

public class TestSession implements Session {
    private final Session realSession;
    private boolean returnNullTransaction = false;

    public TestSession(Session realSession) {
        this.realSession = realSession;
    }

    public void setReturnNullTransaction(boolean value) {
        this.returnNullTransaction = value;
    }

    @Override
    public Transaction beginTransaction() {
        if (returnNullTransaction) {
            return null;
        }
        return realSession.beginTransaction();
    }

    @Override
    public Transaction getTransaction() {
        return null;
    }

    @Override
    public org.hibernate.query.Query createQuery(String s) {
        return null;
    }

    @Override
    public org.hibernate.query.Query getNamedQuery(String s) {
        return null;
    }

    @Override
    public ProcedureCall getNamedProcedureCall(String s) {
        return null;
    }

    @Override
    public ProcedureCall createStoredProcedureCall(String s) {
        return null;
    }

    @Override
    public ProcedureCall createStoredProcedureCall(String s, Class... classes) {
        return null;
    }

    @Override
    public ProcedureCall createStoredProcedureCall(String s, String... strings) {
        return null;
    }

    @Override
    public Criteria createCriteria(Class aClass) {
        return null;
    }

    @Override
    public Criteria createCriteria(Class aClass, String s) {
        return null;
    }

    @Override
    public Criteria createCriteria(String s) {
        return null;
    }

    @Override
    public Criteria createCriteria(String s, String s1) {
        return null;
    }

    @Override
    public Integer getJdbcBatchSize() {
        return 0;
    }

    @Override
    public void setJdbcBatchSize(Integer integer) {

    }

    @Override
    public void close() {
        realSession.close();
    }

    @Override
    public Serializable save(Object object) {
        return realSession.save(object);
    }

    @Override
    public Serializable save(String s, Object o) {
        return null;
    }

    @Override
    public void saveOrUpdate(Object o) {

    }

    @Override
    public void saveOrUpdate(String s, Object o) {

    }

    @Override
    public void persist(Object object) {
        realSession.persist(object);
    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public <T> T find(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public void persist(String s, Object o) {

    }

    @Override
    public <T> T get(Class<T> entityType, Serializable id) {
        return realSession.get(entityType, id);
    }

    @Override
    public <T> T get(Class<T> aClass, Serializable serializable, LockMode lockMode) {
        return null;
    }

    @Override
    public <T> T get(Class<T> aClass, Serializable serializable, LockOptions lockOptions) {
        return null;
    }

    @Override
    public Object get(String s, Serializable serializable) {
        return null;
    }

    @Override
    public Object get(String s, Serializable serializable, LockMode lockMode) {
        return null;
    }

    @Override
    public Object get(String s, Serializable serializable, LockOptions lockOptions) {
        return null;
    }

    @Override
    public String getEntityName(Object o) {
        return "";
    }

    @Override
    public IdentifierLoadAccess byId(String s) {
        return null;
    }

    @Override
    public <T> MultiIdentifierLoadAccess<T> byMultipleIds(Class<T> aClass) {
        return null;
    }

    @Override
    public MultiIdentifierLoadAccess byMultipleIds(String s) {
        return null;
    }

    @Override
    public <T> IdentifierLoadAccess<T> byId(Class<T> aClass) {
        return null;
    }

    @Override
    public NaturalIdLoadAccess byNaturalId(String s) {
        return null;
    }

    @Override
    public <T> NaturalIdLoadAccess<T> byNaturalId(Class<T> aClass) {
        return null;
    }

    @Override
    public SimpleNaturalIdLoadAccess bySimpleNaturalId(String s) {
        return null;
    }

    @Override
    public <T> SimpleNaturalIdLoadAccess<T> bySimpleNaturalId(Class<T> aClass) {
        return null;
    }

    @Override
    public Filter enableFilter(String s) {
        return null;
    }

    @Override
    public Filter getEnabledFilter(String s) {
        return null;
    }

    @Override
    public void disableFilter(String s) {

    }

    @Override
    public SessionStatistics getStatistics() {
        return null;
    }

    @Override
    public boolean isReadOnly(Object o) {
        return false;
    }

    @Override
    public void setReadOnly(Object o, boolean b) {

    }

    @Override
    public <T> RootGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
    }

    @Override
    public RootGraph<?> createEntityGraph(String s) {
        return null;
    }

    @Override
    public RootGraph<?> getEntityGraph(String s) {
        return null;
    }

    @Override
    public <T> T load(Class<T> entityType, Serializable id) {
        return realSession.load(entityType, id);
    }

    @Override
    public Object load(String s, Serializable serializable) {
        return null;
    }

    @Override
    public void load(Object o, Serializable serializable) {

    }

    @Override
    public void replicate(Object o, ReplicationMode replicationMode) {

    }

    @Override
    public void replicate(String s, Object o, ReplicationMode replicationMode) {

    }

    @Override
    public void update(Object object) {
        realSession.update(object);
    }

    @Override
    public void update(String s, Object o) {

    }

    @Override
    public Object merge(Object o) {
        return null;
    }

    @Override
    public Object merge(String s, Object o) {
        return null;
    }

    @Override
    public void delete(Object object) {
        realSession.delete(object);
    }

    @Override
    public void delete(String s, Object o) {

    }

    @Override
    public void lock(Object o, LockMode lockMode) {

    }

    @Override
    public void lock(String s, Object o, LockMode lockMode) {

    }

    @Override
    public LockRequest buildLockRequest(LockOptions lockOptions) {
        return null;
    }

    @Override
    public void refresh(Object o) {

    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void refresh(String s, Object o) {

    }

    @Override
    public void refresh(Object o, LockMode lockMode) {

    }

    @Override
    public void refresh(Object o, LockOptions lockOptions) {

    }

    @Override
    public void refresh(String s, Object o, LockOptions lockOptions) {

    }

    @Override
    public LockMode getCurrentLockMode(Object o) {
        return null;
    }

    @Override
    public Query createFilter(Object o, String s) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void detach(Object o) {

    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return null;
    }

    @Override
    public void setProperty(String s, Object o) {

    }

    @Override
    public Map<String, Object> getProperties() {
        return Map.of();
    }

    @Override
    public String getTenantIdentifier() {
        return realSession.getTenantIdentifier();
    }

    @Override
    public SharedSessionBuilder sessionWithOptions() {
        return null;
    }

    @Override
    public void flush() throws HibernateException {

    }

    @Override
    public void setFlushMode(FlushModeType flushModeType) {

    }

    @Override
    public void setFlushMode(FlushMode flushMode) {

    }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    @Override
    public void lock(Object o, LockModeType lockModeType) {

    }

    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void setHibernateFlushMode(FlushMode flushMode) {

    }

    @Override
    public FlushMode getHibernateFlushMode() {
        return null;
    }

    @Override
    public void setCacheMode(CacheMode cacheMode) {

    }

    @Override
    public CacheMode getCacheMode() {
        return null;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return realSession.getSessionFactory();
    }

    @Override
    public void cancelQuery() throws HibernateException {

    }

    @Override
    public boolean isDirty() throws HibernateException {
        return false;
    }

    @Override
    public boolean isDefaultReadOnly() {
        return false;
    }

    @Override
    public void setDefaultReadOnly(boolean b) {

    }

    @Override
    public Serializable getIdentifier(Object o) {
        return null;
    }

    @Override
    public boolean contains(String s, Object o) {
        return false;
    }

    @Override
    public void evict(Object o) {

    }

    @Override
    public <T> T load(Class<T> aClass, Serializable serializable, LockMode lockMode) {
        return null;
    }

    @Override
    public <T> T load(Class<T> aClass, Serializable serializable, LockOptions lockOptions) {
        return null;
    }

    @Override
    public Object load(String s, Serializable serializable, LockMode lockMode) {
        return null;
    }

    @Override
    public Object load(String s, Serializable serializable, LockOptions lockOptions) {
        return null;
    }

    @Override
    public boolean isOpen() {
        return realSession.isOpen();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return realSession.isConnected();
    }

    @Override
    public Connection disconnect() {
        return realSession.disconnect();
    }

    @Override
    public void reconnect(Connection connection) {
        realSession.reconnect(connection);
    }

    @Override
    public boolean isFetchProfileEnabled(String s) throws UnknownProfileException {
        return false;
    }

    @Override
    public void enableFetchProfile(String s) throws UnknownProfileException {

    }

    @Override
    public void disableFetchProfile(String s) throws UnknownProfileException {

    }

    @Override
    public TypeHelper getTypeHelper() {
        return null;
    }

    @Override
    public LobHelper getLobHelper() {
        return null;
    }

    @Override
    public void addEventListeners(SessionEventListener... sessionEventListeners) {

    }

    @Override
    public <T> org.hibernate.query.Query<T> createQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public org.hibernate.query.Query createNamedQuery(String s) {
        return null;
    }

    @Override
    public <T> org.hibernate.query.Query<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return null;
    }

    @Override
    public org.hibernate.query.Query createQuery(CriteriaUpdate criteriaUpdate) {
        return null;
    }

    @Override
    public org.hibernate.query.Query createQuery(CriteriaDelete criteriaDelete) {
        return null;
    }

    @Override
    public <T> org.hibernate.query.Query<T> createNamedQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
    }

    @Override
    public void joinTransaction() {

    }

    @Override
    public boolean isJoinedToTransaction() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public Object getDelegate() {
        return null;
    }

    @Override
    public NativeQuery createSQLQuery(String s) {
        return null;
    }

    @Override
    public NativeQuery createNativeQuery(String s) {
        return null;
    }

    @Override
    public NativeQuery createNativeQuery(String s, Class aClass) {
        return null;
    }

    @Override
    public NativeQuery createNativeQuery(String s, String s1) {
        return null;
    }

    @Override
    public NativeQuery getNamedNativeQuery(String s) {
        return null;
    }

    @Override
    public Session getSession() {
        return null;
    }
}
