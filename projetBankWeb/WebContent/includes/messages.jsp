<%@ include file="/includes/TagLibs.jsp" %>
							<c:if test="${erreur != null}">
					    		<script>
					    			var n = noty({
					    			    layout: 'center',
					    				type: 'error', 
					    				text: '<c:out value="${erreur}"/>',
					    			    animation: {
					    			        open: {height: 'toggle'},
									        close: {height: 'toggle'}
					    			    },
									    closeWith: ['click'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications
					    			});
					    		</script>
					    	</c:if>
							<c:if test="${succes != null}">
					    		<script>
					    			var n = noty({
					    			    layout: 'center',
					    				type: 'success', 
					    				text: '<c:out value="${succes}"/>',
					    			    animation: {
					    			        open: {height: 'toggle'}, 
									        close: {height: 'toggle'}
					    			    },
									    closeWith: ['click'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications
					    			});
					    		</script>
					    	</c:if>
