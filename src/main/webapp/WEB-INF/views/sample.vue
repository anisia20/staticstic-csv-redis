<template lang="html"> 
	<div class="column" id = "column-1">
	First Category
	
	  <div class="portlet">
	    <div class="portlet-header">Feeds</div>
	    <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	  </div>
	
	  <div class="portlet">
	    <div class="portlet-header">News</div>
	    <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	  </div>
	
	</div>
	
	<div class="column" id = "column-2">
	Second Category
	
	  <div class="portlet">
	    <div class="portlet-header">Shopping</div>
	    <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	  </div>
	
	</div>
	
	<div class="column" id = "column-3">
	Third Category
	
	  <div class="portlet">
	    <div class="portlet-header">Shopping</div>
	    <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	  </div>
	
	  <div class="portlet">
	    <div class="portlet-header">Shopping</div>
	    <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	  </div>
	
	</div>
	
	<div class="column" id = "column-4">
	Fourth Category
	
	  <div class="portlet">
	    <div class="portlet-header">Links</div>
	    <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	  </div>
	
	  <div class="portlet">
	    <div class="portlet-header">Images</div>
	    <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	  </div>
	
	</div> 
</template> 

<script> 
	export default { 
		data () { 
			return { msg: "Hello World" } 
		}
		
	} 
</script> 

<style scoped> 
  body {
    min-width: 520px;
  }
  .column {
    width: 170px;
    float: left;
    padding-bottom: 100px;
  }
  .portlet {
    margin: 0 1em 1em 0;
    padding: 0.3em;
  }
  .portlet-header {
    padding: 0.2em 0.3em;
    margin-bottom: 0.5em;
    position: relative;
  }
  .portlet-toggle {
    position: absolute;
    top: 50%;
    right: 0;
    margin-top: -8px;
  }
  .portlet-content {
    padding: 0.4em;
  }
  .portlet-placeholder {
    border: 1px dotted black;
    margin: 0 1em 1em 0;
    height: 50px;
  }
</style>
