//package cn.com.linnax.file.service.impl;
//
//import cn.com.linnax.file.dao.FileinfoMapper;
//import cn.com.linnax.file.model.Fileinfo;
//import cn.com.linnax.file.model.FileinfoExample;
//import cn.com.linnax.file.model.PageBean;
//import cn.com.linnax.file.service.FileService;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//
//@Service("fileService")
//public class FileServiceImpl implements FileService {
//	
//	@Autowired
//	FileinfoMapper fileinfoMapper;
//	
//	@Override
//	public List<Fileinfo> queryAllFilePO(PageBean pageBean,Fileinfo fileinfo) throws Exception {
//		FileinfoExample example = new FileinfoExample();
//		if(pageBean != null){
//			example.setLimitStart(pageBean.getBegin());
//			example.setLimitEnd(pageBean.getRows());
//		}
//		
////		Criteria criteria = example.createCriteria();
//		
//		if(fileinfo != null){
//			if(StringUtils.isNotEmpty(fileinfo.getName())){
//				
//				example.createCriteria().andNameLike("%"+fileinfo.getName()+"%");
//				
//			}
//		}
//
//		List<Fileinfo> list=fileinfoMapper.selectByExample(example);
//		return list;
//	}
//
//	@Override
//	public List<Fileinfo> queryAllFilePO() throws Exception {
//		List<Fileinfo> list=fileinfoMapper.selectByExample(null);
//		return list;
//	}
//
//	@Override
//	public int totalNumber() throws Exception {
//		return fileinfoMapper.totalNumber();
//	}
////
////	@Override
////	public void updateFile(Fileinfo fileinfo) {
////		
////	}
//
//	@Override
//	public void deleteById(Integer id) {
//		fileinfoMapper.deleteByPrimaryKey(id);
//	}
//
//	@Override
//	public void deleteFileList(List<Integer> ids) {
//		FileinfoExample example = new FileinfoExample();
//		example.createCriteria().andIdIn(ids);
//		fileinfoMapper.deleteByExample(example);
//	}
//
//}
