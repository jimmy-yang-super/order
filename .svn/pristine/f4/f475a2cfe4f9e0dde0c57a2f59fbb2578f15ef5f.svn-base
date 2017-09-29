package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;

import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.service.FileattachmentService;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
/**
 * 附件控制类
 * @author wuyin
 *
 */
@Path("/order")
@CheckCookie
public class FileattachmentController extends AbstractController{
	@Path("/fileattachmentinit")
	public ActionResult fileattachmentinit(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String busId = beat().getRequest().getParameter("busid");
				String text =FileattachmentService.getInstance().initFileattachmeetdis(busId);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	@Path("/addFileattach")
	public ActionResult addFileattach(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String busId = beat().getRequest().getParameter("busid");
				String kehId = beat().getRequest().getParameter("kehId");
				String field = beat().getRequest().getParameter("field");
				String fileId = beat().getRequest().getParameter("fileId");
				String text = FileattachmentService.getInstance().saveFileattach(busId, kehId, field, fileId);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	@Path("/displayUpload")
	public ActionResult displayUpload(){
		return new ActionResult() {
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String kehId = beat().getRequest().getParameter("kehId");
				String field = beat().getRequest().getParameter("field");
				String text = FileattachmentService.getInstance().getInituploadinfo(kehId, field);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	@Path("/deleFile")
	public ActionResult deleFile(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String kehId = beat().getRequest().getParameter("kehId");
				String field = beat().getRequest().getParameter("field");
				String fileId = beat().getRequest().getParameter("fileId");
				String text = FileattachmentService.getInstance().delUploadFile(kehId, field, fileId);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	@Path("/addotherFileattach")
	public ActionResult addotherFileattach(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String filebusid = beat().getRequest().getParameter("filebusid");
				String field = beat().getRequest().getParameter("field");
				String fileId = beat().getRequest().getParameter("fileId");
				String text =FileattachmentService.getInstance().saveOtherFile(filebusid,field,fileId);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		};
	}
	
	@Path("/displayOtherUpload")
	public ActionResult displayOtherUpload(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String filaattid = beat().getRequest().getParameter("filaattid");
				String field = beat().getRequest().getParameter("field");
				String text = FileattachmentService.getInstance().getDisOtherJson(filaattid,field);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}

	@Path("/deleOtherFile")
	public ActionResult deleOtherFile(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String filaattid = beat().getRequest().getParameter("fileattid");
				String field = beat().getRequest().getParameter("field");
				String fileId = beat().getRequest().getParameter("fileId");
				String text = FileattachmentService.getInstance().delOtherFile(filaattid,field,fileId);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	} 
	
	@Path("/submitBusiness")
	public ActionResult submitBusiness(){
		return new ActionResult() {
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String fbuid = beat().getRequest().getParameter("fbuid");
				String text =FileattachmentService.getInstance().submitBusenessinfo(fbuid);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	@Path("/validateFileattach")
	public ActionResult validateFileattach(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String fbuid = beat().getRequest().getParameter("fbuid");
				String text =FileattachmentService.getInstance().validateFileattach(fbuid);
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
