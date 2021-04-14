<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/component/demogrid.jsp" %> 
<script>
const redisboard = Vue.component('redisboard',{
      template : `	
<div id="demo">
  <form id="search">
    Search <input name="query" v-model="searchQuery">
  </form>
  <demo-grid
    :heroes="gridData"
    :columns="gridColumns"
    :filter-key="searchQuery">
  </demo-grid>
</div>
	  `,
	  data() {
	      return {
			 searchQuery: '',
			    gridColumns: ['name', 'power'],
			    gridData: [
			      { name: 'Chuck Norris', power: Infinity },
			      { name: 'Bruce Lee', power: 9000 },
			      { name: 'Jackie Chan', power: 7000 },
			      { name: 'Jet Li', power: 8000 }
			      ]
	      }
	    },
	    methods: {
	       
	  
	    }
})

</script>


