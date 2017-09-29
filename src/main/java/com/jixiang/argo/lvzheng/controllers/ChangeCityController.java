package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.util.List;

import com.jixiang.argo.lvzheng.service.AddOrderRealService;
import com.jixiang.argo.lvzheng.service.ChangeCityService;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.ProductCategoryEntity;

@Path("/order")
public class ChangeCityController extends AbstractController{

	@Path("/changeCity")
	public ActionResult changeCity(){
		return new ActionResult() {
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");

				String productId = beat().getRequest()
						.getParameter("productId");
				String cityId = beat().getRequest().getParameter("city");
				String text = "";
				if (StringUtil.isEmpty(productId)) {
					if ("1101".equals(productId)) {
						if ("2".equals(cityId)) {
							productId = "2201";
						}
					}
					if ("2201".equals(productId)) {
						if ("1".equals(cityId)) {
							productId = "1101";
						}
					}
					if ("1105".equals(productId)) {
						if ("2".equals(cityId)) {
							productId = "2202";
						}
					}
					if ("2202".equals(productId)) {
						if ("1".equals(cityId)) {
							productId = "1105";
						}
					}
					if (StringUtil.isEmpty(productId)) {
						List<ProductCategoryEntity> changein = AddOrderRealService
								.getInstance().changeCityinfoList(productId);
						// 拼接公司注册
						if ("1101".equals(productId)|| "2201".equals(productId)) {
							text = ChangeCityService.getInstance().backGszcinfo(changein,cityId);
						}
						// 拼接代理记账
						if ("1105".equals(productId)|| "2202".equals(productId)) {
							text = ChangeCityService.getInstance().backDljzinfo(changein,cityId);
						}
					}
				}
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
