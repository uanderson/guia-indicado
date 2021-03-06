/*
 * jQuery Iframe Transport Plugin 1.5
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2011, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

(function(a){if(typeof define==="function"&&define.amd){define(["jquery"],a)}else{a(window.jQuery)}}(function(b){var a=0;b.ajaxTransport("iframe",function(c){if(c.async&&(c.type==="POST"||c.type==="GET")){var e,d;return{send:function(f,g){e=b('<form style="display:none;"></form>');e.attr("accept-charset",c.formAcceptCharset);d=b('<iframe src="javascript:false;" name="iframe-transport-'+(a+=1)+'"></iframe>').bind("load",function(){var h,i=b.isArray(c.paramName)?c.paramName:[c.paramName];d.unbind("load").bind("load",function(){var j;try{j=d.contents();if(!j.length||!j[0].firstChild){throw new Error()}}catch(k){j=undefined}g(200,"success",{iframe:j});b('<iframe src="javascript:false;"></iframe>').appendTo(e);e.remove()});e.prop("target",d.prop("name")).prop("action",c.url).prop("method",c.type);if(c.formData){b.each(c.formData,function(j,k){b('<input type="hidden"/>').prop("name",k.name).val(k.value).appendTo(e)})}if(c.fileInput&&c.fileInput.length&&c.type==="POST"){h=c.fileInput.clone();c.fileInput.after(function(j){return h[j]});if(c.paramName){c.fileInput.each(function(j){b(this).prop("name",i[j]||c.paramName)})}e.append(c.fileInput).prop("enctype","multipart/form-data").prop("encoding","multipart/form-data")}e.submit();if(h&&h.length){c.fileInput.each(function(k,j){var l=b(h[k]);b(j).prop("name",l.prop("name"));l.replaceWith(j)})}});e.append(d).appendTo(document.body)},abort:function(){if(d){d.unbind("load").prop("src","javascript".concat(":false;"))}if(e){e.remove()}}}}});b.ajaxSetup({converters:{"iframe text":function(c){return b(c[0].body).text()},"iframe json":function(c){return b.parseJSON(b(c[0].body).text())},"iframe html":function(c){return b(c[0].body).html()},"iframe script":function(c){return b.globalEval(b(c[0].body).text())}}})}));