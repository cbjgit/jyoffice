sample
===
* 注释

	select #use("cols")# from frm_publish where #use("condition")#

cols
===

	form_id,form_html,publish_date

updateSample
===

	`form_id`=#formId#,`form_html`=#formHtml#,`publish_date`=#publishDate#

condition
===

	1 = 1  
	@if(!isEmpty(formHtml)){
	 and `form_html`=#formHtml#
	@}
	@if(!isEmpty(publishDate)){
	 and `publish_date`=#publishDate#
	@}
	
