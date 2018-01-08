//package cn.com.linnax.file.service.impl;
//
//import cn.com.linnax.file.dao.XiDongWangMapper;
//import cn.com.linnax.file.model.PageBean;
//import cn.com.linnax.file.model.XiDongWangExample;
//import cn.com.linnax.file.model.XiDongWangWithBLOBs;
//import cn.com.linnax.file.service.XiDongWangService;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service("xiDongWangService")
//public class XiDongWangServiceImpl implements XiDongWangService {
//	@Autowired
//	XiDongWangMapper xiDongWangMapper;
//	
//	@Override
//	public List<XiDongWangWithBLOBs> queryAllFiles(PageBean pageBean,
//												   XiDongWangWithBLOBs xiDongWang) throws Exception {
//		
//		XiDongWangExample example = new XiDongWangExample();
//		if(pageBean != null){
//			example.setLimitStart(pageBean.getBegin());
//			example.setLimitEnd(pageBean.getRows());
//		}
//		
////		Criteria criteria = example.createCriteria();
//		
//		if(xiDongWang != null){
//			if(StringUtils.isNotEmpty(xiDongWang.getTopNav())){
//				example.createCriteria().andTopNavLike("%"+xiDongWang.getTopNav()+"%");
//			}
//			
//			if(StringUtils.isNotEmpty(xiDongWang.getSubNav())){
//				example.createCriteria().andSubNavLike("%"+xiDongWang.getSubNav()+"%");
//			}
//			
//			if(StringUtils.isNotEmpty(xiDongWang.getDownload())){
//				example.createCriteria().andDownloadLike("%"+xiDongWang.getDownload()+"%");
//			}
//			
//		}
//
//		List<XiDongWangWithBLOBs> list=xiDongWangMapper.selectByExampleWithBLOBs(example);
//		return list;
//	}
//
//	@Override
//	public int totalNumber() throws Exception {
//		return xiDongWangMapper.totalNumber();
//	}
//
//}
