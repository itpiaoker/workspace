package cn.com.linnax.file.model;

import java.util.ArrayList;
import java.util.List;

public class XiDongWangExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    protected Integer limitEnd;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public XiDongWangExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public Integer getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAidIsNull() {
            addCriterion("aid is null");
            return (Criteria) this;
        }

        public Criteria andAidIsNotNull() {
            addCriterion("aid is not null");
            return (Criteria) this;
        }

        public Criteria andAidEqualTo(Integer value) {
            addCriterion("aid =", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotEqualTo(Integer value) {
            addCriterion("aid <>", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThan(Integer value) {
            addCriterion("aid >", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThanOrEqualTo(Integer value) {
            addCriterion("aid >=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThan(Integer value) {
            addCriterion("aid <", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThanOrEqualTo(Integer value) {
            addCriterion("aid <=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidIn(List<Integer> values) {
            addCriterion("aid in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotIn(List<Integer> values) {
            addCriterion("aid not in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidBetween(Integer value1, Integer value2) {
            addCriterion("aid between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotBetween(Integer value1, Integer value2) {
            addCriterion("aid not between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNull() {
            addCriterion("typeid is null");
            return (Criteria) this;
        }

        public Criteria andTypeidIsNotNull() {
            addCriterion("typeid is not null");
            return (Criteria) this;
        }

        public Criteria andTypeidEqualTo(Short value) {
            addCriterion("typeid =", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotEqualTo(Short value) {
            addCriterion("typeid <>", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThan(Short value) {
            addCriterion("typeid >", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidGreaterThanOrEqualTo(Short value) {
            addCriterion("typeid >=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThan(Short value) {
            addCriterion("typeid <", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidLessThanOrEqualTo(Short value) {
            addCriterion("typeid <=", value, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidIn(List<Short> values) {
            addCriterion("typeid in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotIn(List<Short> values) {
            addCriterion("typeid not in", values, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidBetween(Short value1, Short value2) {
            addCriterion("typeid between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andTypeidNotBetween(Short value1, Short value2) {
            addCriterion("typeid not between", value1, value2, "typeid");
            return (Criteria) this;
        }

        public Criteria andRedirecturlIsNull() {
            addCriterion("redirecturl is null");
            return (Criteria) this;
        }

        public Criteria andRedirecturlIsNotNull() {
            addCriterion("redirecturl is not null");
            return (Criteria) this;
        }

        public Criteria andRedirecturlEqualTo(String value) {
            addCriterion("redirecturl =", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlNotEqualTo(String value) {
            addCriterion("redirecturl <>", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlGreaterThan(String value) {
            addCriterion("redirecturl >", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlGreaterThanOrEqualTo(String value) {
            addCriterion("redirecturl >=", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlLessThan(String value) {
            addCriterion("redirecturl <", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlLessThanOrEqualTo(String value) {
            addCriterion("redirecturl <=", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlLike(String value) {
            addCriterion("redirecturl like", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlNotLike(String value) {
            addCriterion("redirecturl not like", value, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlIn(List<String> values) {
            addCriterion("redirecturl in", values, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlNotIn(List<String> values) {
            addCriterion("redirecturl not in", values, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlBetween(String value1, String value2) {
            addCriterion("redirecturl between", value1, value2, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andRedirecturlNotBetween(String value1, String value2) {
            addCriterion("redirecturl not between", value1, value2, "redirecturl");
            return (Criteria) this;
        }

        public Criteria andTempletIsNull() {
            addCriterion("templet is null");
            return (Criteria) this;
        }

        public Criteria andTempletIsNotNull() {
            addCriterion("templet is not null");
            return (Criteria) this;
        }

        public Criteria andTempletEqualTo(String value) {
            addCriterion("templet =", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletNotEqualTo(String value) {
            addCriterion("templet <>", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletGreaterThan(String value) {
            addCriterion("templet >", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletGreaterThanOrEqualTo(String value) {
            addCriterion("templet >=", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletLessThan(String value) {
            addCriterion("templet <", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletLessThanOrEqualTo(String value) {
            addCriterion("templet <=", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletLike(String value) {
            addCriterion("templet like", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletNotLike(String value) {
            addCriterion("templet not like", value, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletIn(List<String> values) {
            addCriterion("templet in", values, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletNotIn(List<String> values) {
            addCriterion("templet not in", values, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletBetween(String value1, String value2) {
            addCriterion("templet between", value1, value2, "templet");
            return (Criteria) this;
        }

        public Criteria andTempletNotBetween(String value1, String value2) {
            addCriterion("templet not between", value1, value2, "templet");
            return (Criteria) this;
        }

        public Criteria andUseripIsNull() {
            addCriterion("userip is null");
            return (Criteria) this;
        }

        public Criteria andUseripIsNotNull() {
            addCriterion("userip is not null");
            return (Criteria) this;
        }

        public Criteria andUseripEqualTo(String value) {
            addCriterion("userip =", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripNotEqualTo(String value) {
            addCriterion("userip <>", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripGreaterThan(String value) {
            addCriterion("userip >", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripGreaterThanOrEqualTo(String value) {
            addCriterion("userip >=", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripLessThan(String value) {
            addCriterion("userip <", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripLessThanOrEqualTo(String value) {
            addCriterion("userip <=", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripLike(String value) {
            addCriterion("userip like", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripNotLike(String value) {
            addCriterion("userip not like", value, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripIn(List<String> values) {
            addCriterion("userip in", values, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripNotIn(List<String> values) {
            addCriterion("userip not in", values, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripBetween(String value1, String value2) {
            addCriterion("userip between", value1, value2, "userip");
            return (Criteria) this;
        }

        public Criteria andUseripNotBetween(String value1, String value2) {
            addCriterion("userip not between", value1, value2, "userip");
            return (Criteria) this;
        }

        public Criteria andDownloadIsNull() {
            addCriterion("download is null");
            return (Criteria) this;
        }

        public Criteria andDownloadIsNotNull() {
            addCriterion("download is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadEqualTo(String value) {
            addCriterion("download =", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadNotEqualTo(String value) {
            addCriterion("download <>", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadGreaterThan(String value) {
            addCriterion("download >", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadGreaterThanOrEqualTo(String value) {
            addCriterion("download >=", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadLessThan(String value) {
            addCriterion("download <", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadLessThanOrEqualTo(String value) {
            addCriterion("download <=", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadLike(String value) {
            addCriterion("download like", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadNotLike(String value) {
            addCriterion("download not like", value, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadIn(List<String> values) {
            addCriterion("download in", values, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadNotIn(List<String> values) {
            addCriterion("download not in", values, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadBetween(String value1, String value2) {
            addCriterion("download between", value1, value2, "download");
            return (Criteria) this;
        }

        public Criteria andDownloadNotBetween(String value1, String value2) {
            addCriterion("download not between", value1, value2, "download");
            return (Criteria) this;
        }

        public Criteria andTopNavIsNull() {
            addCriterion("top_nav is null");
            return (Criteria) this;
        }

        public Criteria andTopNavIsNotNull() {
            addCriterion("top_nav is not null");
            return (Criteria) this;
        }

        public Criteria andTopNavEqualTo(String value) {
            addCriterion("top_nav =", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavNotEqualTo(String value) {
            addCriterion("top_nav <>", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavGreaterThan(String value) {
            addCriterion("top_nav >", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavGreaterThanOrEqualTo(String value) {
            addCriterion("top_nav >=", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavLessThan(String value) {
            addCriterion("top_nav <", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavLessThanOrEqualTo(String value) {
            addCriterion("top_nav <=", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavLike(String value) {
            addCriterion("top_nav like", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavNotLike(String value) {
            addCriterion("top_nav not like", value, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavIn(List<String> values) {
            addCriterion("top_nav in", values, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavNotIn(List<String> values) {
            addCriterion("top_nav not in", values, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavBetween(String value1, String value2) {
            addCriterion("top_nav between", value1, value2, "topNav");
            return (Criteria) this;
        }

        public Criteria andTopNavNotBetween(String value1, String value2) {
            addCriterion("top_nav not between", value1, value2, "topNav");
            return (Criteria) this;
        }

        public Criteria andSubNavIsNull() {
            addCriterion("sub_nav is null");
            return (Criteria) this;
        }

        public Criteria andSubNavIsNotNull() {
            addCriterion("sub_nav is not null");
            return (Criteria) this;
        }

        public Criteria andSubNavEqualTo(String value) {
            addCriterion("sub_nav =", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavNotEqualTo(String value) {
            addCriterion("sub_nav <>", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavGreaterThan(String value) {
            addCriterion("sub_nav >", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavGreaterThanOrEqualTo(String value) {
            addCriterion("sub_nav >=", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavLessThan(String value) {
            addCriterion("sub_nav <", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavLessThanOrEqualTo(String value) {
            addCriterion("sub_nav <=", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavLike(String value) {
            addCriterion("sub_nav like", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavNotLike(String value) {
            addCriterion("sub_nav not like", value, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavIn(List<String> values) {
            addCriterion("sub_nav in", values, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavNotIn(List<String> values) {
            addCriterion("sub_nav not in", values, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavBetween(String value1, String value2) {
            addCriterion("sub_nav between", value1, value2, "subNav");
            return (Criteria) this;
        }

        public Criteria andSubNavNotBetween(String value1, String value2) {
            addCriterion("sub_nav not between", value1, value2, "subNav");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table xi_dong_wang
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table xi_dong_wang
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}