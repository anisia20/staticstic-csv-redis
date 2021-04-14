<%@ page language="java" pageEncoding="utf-8" %>
<script>
const uploadcsv = Vue.component('uploadcsv',{
      template : `	
	  <div class="container">
	    <label>.csv 파일만 가능 </label>
	        <form @submit.prevent="onSubmit">
	            <div class="form-group">
	                <input type="file" @change="onFileUpload" accept='.csv'>
	            </div>
	            <div class="form-group">
	                <button class="btn btn-primary btn-block btn-lg">전송</button>
	            </div>
	        </form>
	    </div>    
	  </div>`,
	  data() {
	      return {
	         FILE: null,
	         name: ''
	      };
	    },
	    methods: {
	        onFileUpload (event) {
	          this.FILE = event.target.files[0]
	        },
	        onSubmit() {
	          // upload file
	          const formData = new FormData()
	          formData.append('avatar', this.FILE, this.FILE.name)
	          formData.append('name', this.name)
	          axios.post('/front/scouter/file/upload', formData, {
	          }).then((res) => {
	            console.log(res)
	          })
	          
	          
	        }  
	    }
})

</script>