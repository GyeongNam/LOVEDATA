/**
 * 
 */
function find_id_popup() {
	window.open("<%= request.getContextPath() %>/user/find-id.jsp" ,
	"FIndUserID",
	"width=400,height=400,left=600");
}
