<%@ page language="java" pageEncoding="utf-8" %>

<!-- scouter -->
<%@ include file="/WEB-INF/views/layout/layout.jsp" %>
<%@ include file="/WEB-INF/views/contents/scouter/uploadcsv.jsp" %>
<%@ include file="/WEB-INF/views/contents/redis/redishash.jsp" %>
<%@ include file="/WEB-INF/views/contents/board/redisboard.jsp" %>

<script>
const routes = [
	  { path: '/uploadcsv', component: uploadcsv },
	  { path: '/redishash', component: redishash },
	  { path: '/redisboard', component: redisboard },

	 
	]

const router = new VueRouter({
	  routes // `routes: routes`의 줄임
	})
</script>