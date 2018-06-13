package com.mingsoft.bbs.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.basic.parser.IGeneralParser;
import com.mingsoft.bbs.bean.StatisticsBean;
import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.biz.ISubjectCommentBiz;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.bbs.constant.ModelCode;
import com.mingsoft.bbs.constant.e.SubjectEnum;
import com.mingsoft.bbs.entity.ForumEntity;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.bbs.parser.impl.AuthorIconParser;
import com.mingsoft.bbs.parser.impl.AuthorIdParser;
import com.mingsoft.bbs.parser.impl.AuthorParser;
import com.mingsoft.bbs.parser.impl.CategoryParser;
import com.mingsoft.bbs.parser.impl.CommentDateLastParser;
import com.mingsoft.bbs.parser.impl.CommentListParser;
import com.mingsoft.bbs.parser.impl.ContentParser;
import com.mingsoft.bbs.parser.impl.DateParser;
import com.mingsoft.bbs.parser.impl.ListParser;
import com.mingsoft.bbs.parser.impl.PostionParser;
import com.mingsoft.bbs.parser.impl.PreviousNextParser;
import com.mingsoft.bbs.parser.impl.TotalCommentParser;
import com.mingsoft.bbs.parser.impl.TotalPeopleParser;
import com.mingsoft.bbs.parser.impl.TotalPeopleSubjectParser;
import com.mingsoft.bbs.parser.impl.TotalSubjectParser;
import com.mingsoft.bbs.parser.impl.TotalTodayParser;
import com.mingsoft.bbs.parser.impl.TotalYesterdayParser;
import com.mingsoft.bbs.parser.impl.TypeParser;
import com.mingsoft.cms.parser.impl.ChannelParser;
import net.mingsoft.comment.entity.CommentEntity;
import com.mingsoft.parser.impl.general.PageNumParser;
import com.mingsoft.parser.impl.general.PageParser;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * 
 * 
 * 
 * 
 * <p>
 * <b>铭飞科技</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015 
 * </p>
 * 
 * @author killfen 
 * 
 * <p>
 * Comments:论坛标签解析
 * </p>
 * 
 * <p>
 * Create Date:2015-5-8
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
@Component
@Scope("prototype")
public class BbsParser extends IGeneralParser {

	/**
	 * 帖子业务层
	 */
	@Autowired
	private ISubjectBiz subjectBiz;
	
	/**
	 * 评论业务层
	 */
	@Autowired
	private ISubjectCommentBiz subjectCommentBiz;
	

	
	/**
	 *上一篇贴\下一篇贴
	 */
	private BasicEntity  previous, next;
	
	
	
	/**
	 *  当前贴
	 */
	private SubjectEntity subject;
		
	
	/**
	 * 当前页码
	 */
	private int curPageNo = 1;
	

	
	/**
	 * 列表连接地址
	 */
	private String listLinkPath;

	/**
	 * 获取类别
	 */
	private ForumEntity forum;
	
	/**
	 * 栏目逻辑层注入
	 */
	@Autowired
	private IForumBiz categoryBiz;
	
	/**
	 * 注入模块层
	 */
	@Autowired
	private IModelBiz modelbiz;
	

	
	
	
	/**
	 * 当前栏目编号,查询子栏目或该栏目下的帖子列表时用到
	 */
	private int curCategoryId;
	
	
	private List<CategoryEntity> postion;
	
	public static final String  POSTION = "postion";
	
	/**
	 *  关键字,提供给map 做键值
	 */
	public static final String KEYWORD = "keyWord";
	
	/**
	 * 用户id供解析功能权限时，判断该用户是否拥有权限
	 */
	public static final String PEOPLEID="peopleId";
	
	/**
	 * 关键字
	 */
	private String keyWord = null;
	
	/**
	 * 默认发帖作者为匿名
	 */
	protected final static String  AUTHOR ="<!--未找到该标签内容-->";
	
	/**
	 * 默认发帖作者头像
	 */
	protected final static String  AUTHOR_ICON ="<!--未找到该标签内容-->";
	
	private List<SubjectEntity> subjectSearchList = null;
	
	/**
	 * 搜索列表数据
	 */
	public  final static String  SUBJECT_SEARCH_LIST ="subjectSearchList";
	
	/**
	 * 搜索时的分页对象
	 */
	public final static String PAGE="pageUtil";
	
	/**
	 * 初始化论坛解析器
	 */
	public void init(Object... obj) {
		super.init(obj);
		//将上一次请求的参数必须重置
		mobilePath = "";
		keyWord = null;
		this.page = null;
		this.listLinkPath = null;
		this.forum = null;
		this.curCategoryId = -1;
		subjectSearchList = null;
		//开始解析需要的参数名
		for (Object o : obj) {
			if (o != null) {
				if (o instanceof Map) {
					Map temp = (Map) o;
					if (temp.get(PREVIOUS) instanceof BasicEntity) {
						previous =(BasicEntity)temp.get(PREVIOUS);
					}
					if (temp.get(NEXT) instanceof BasicEntity) {
						next =(BasicEntity)temp.get(NEXT);
					}			
					if (StringUtil.isInteger(temp.get(CUR_PAGE_NO) )) {
						curPageNo = Integer.parseInt(temp.get(CUR_PAGE_NO)+"");
					}		
					if (!StringUtil.isBlank(temp.get(LIST_LINK_PATH) )) {
						listLinkPath = temp.get(LIST_LINK_PATH)+"";
					}			
					if (!StringUtil.isBlank(temp.get(MOBILE) )) {
						mobilePath = temp.get(MOBILE)+"";
					}
					if (!StringUtil.isBlank(temp.get(KEYWORD) )) {
						keyWord = temp.get(KEYWORD)+"";
					}		
					if (!StringUtil.isBlank(temp.get(POSTION) )) {
						postion = (List)temp.get(POSTION);
					}							
					if (!StringUtil.isBlank(temp.get(MODEL_ID) )) {
						this.modelId = Integer.parseInt(temp.get(MODEL_ID)+"");
					}
					if (temp.get(SUBJECT_SEARCH_LIST)  instanceof  java.util.List) {
						//搜索时候的文章列表数据
						subjectSearchList = (List)temp.get(SUBJECT_SEARCH_LIST);
					}
					if (temp.get(PAGE) instanceof PageUtil) { //显示商品搜索的时候必须存在
						page = (PageUtil)temp.get(PAGE);
					}
					
				}
				if (o instanceof ForumEntity) {
					forum = (ForumEntity)o;
					this.curCategoryId = forum.getCategoryId();
				}
				if (o instanceof BasicEntity) {
					subject = (SubjectEntity)o;
					forum = (ForumEntity) subject.getSubjectForum();
				}
				
			} 
		}
	}
	
	
	
	
	/**
	 * obj:
	 */
	@Override
	public String parse(String html,Object... obj) {
		super.htmlContent = html;
		this.init(obj);
		// TODO Auto-generated method stub
		htmlContent = parseGeneral();
		htmlContent = parseSearchList();
		htmlContent = parseForum();
		htmlContent = parseArclist();
		htmlContent = parseSubject();
		htmlContent = parseComlist();
		htmlContent = parseList();
		htmlContent = parseStatistics();
		htmlContent = parseChannel();
		htmlContent = parsePage();
		htmlContent = parsePostion();
		return htmlContent;
	}
	
	/**
	 * 查找页面中分页标签中出现的size的值
	 * 
	 * @param htmlContent
	 * @param column
	 * @param curPageNo
	 * @return
	 */
	public int getPageSize(AppEntity website, String htmlContent) {
		// 页面总数，默认为1
		int pageSize = 1;
		// 当前列表标签中属性的集合-------------------
		Map<String, String> property = ListParser.listProperty(htmlContent, true);
		// 没有找到分页标签标签
		if (property ==null) {
			return pageSize;
		}
		String isPaging = property.get(ListParser.LIST_ISPAGING);
		if (!StringUtil.isBlank(isPaging) && isPaging.equals("true")) {
			// 列表每页显示的数量
			int size = StringUtil.string2Int(property.get(ListParser.LIST_SIZE));
			// 数据库中该栏目下文章的总数
			int subjectCount = subjectBiz.countByForumId(app.getAppId(), forum.getCategoryId(),SubjectEnum.DISPLAY);
			// 当用户知道的显示数量小于0或大于文章实际总数时
			if (size <= 0 || size > subjectCount) {
				size = subjectCount;
			}
			// 如果文章总数为0则分页数量为1
			if (size == 0) {
				pageSize = 1;
				return pageSize;
			}
			pageSize = subjectCount % size >= 1 ? subjectCount / size + 1 : subjectCount / size;
		}
		return pageSize;
	}
	
	/**
	 * 对搜索列表中的标签的解析
	 * @return
	 */
	public String parseSearchList() {
		Map<String, String> property = ListParser.listProperty(htmlContent, true);
		if (StringUtil.isBlank(property)) { // 没有找到分页标签标签
			return htmlContent;
		}
		String isPaging = property.get(ListParser.LIST_ISPAGING);
		if (isPaging != null && isPaging.equals("true")) {
			//如果指定了板块Id则，取该板块下的帖子列表，而不是搜索的帖子列表
			int categoryId=StringUtil.string2Int(property.get(ListParser.LIST_TYPEID));
			if(categoryId!=0){
				return htmlContent;
			}
			if(subjectSearchList!=null){
				// 替换列表标签
				htmlContent = new com.mingsoft.bbs.parser.impl.ListParser(htmlContent, subjectSearchList, getAppUrl(), property, true).parse();
			}
		}
		return htmlContent;
	}
	
	
	/**
	 * 解析列表标签
	 * 
	 * @param htmlContent
	 *            模版内容
	 * @param linkColumnId
	 *            栏目编号
	 * @return　替换好的内容
	 */
	private String parseArclist() {

		// 查找当前模版页面拥有多少个列表标签
		int listNum = ListParser.countArcList(super.htmlContent);
		// 替换完分页标签后的HTML代码
		for (int i = 0; i < listNum; i++) {
			// 当前列表标签中属性的集合-------------------
			Map<String, String> property = ListParser.listProperty(super.htmlContent, false);
			// 取当前标签下的栏目ID
			// 列表每页显示的数量
			int size = StringUtil.string2Int(property.get(ListParser.LIST_SIZE));
			//获取标签下的板块id
			int categoryId=StringUtil.string2Int(property.get(ListParser.LIST_TYPEID));
			//推荐属性
			String flag = property.get(ListParser.LIST_FLAG);
			//不推荐属性
			String noFlag = property.get(ListParser.LIST_NOFLAG);
			
			
			
			//没有指定栏目id，则指定为当前板块的id
			if(StringUtil.isBlank(categoryId) || categoryId==0){
				if(forum!=null){
					// 数据库中该栏目下文章的总数
					categoryId=forum.getCategoryId();
				}
			}
			//取出帖子总数
			int subjectCount = subjectBiz.getCountByForumIdAndSubjectTypeId(app.getAppId(), categoryId, null, flag, noFlag,SubjectEnum.DISPLAY);
			// 如果没有指定文章每页显示数量则显示所有数量
			if (size <= 0 || size > subjectCount) {
				size = subjectCount;
			}
			if(StringUtil.isBlank(listLinkPath)){
				return htmlContent;
			}
			//依据排序字段
			String orderBy = property.get(ListParser.LIST_ORDERBY);
			//排序方式
			String order = property.get(ListParser.LIST_ORDER);
			//默认是降序
			boolean _order=false;
			if(!StringUtil.isBlank(order) && order.equals("asc")){
				_order=true;
			}
			PageUtil page = new PageUtil(curPageNo, size, subjectCount, listLinkPath);
			// 从数据库取出文章列表数组
			List<SubjectEntity> listSubjects = subjectBiz.queryByForumIdAndSubjectTypeId(app.getAppId(),categoryId, null, page,flag,noFlag,orderBy,_order,SubjectEnum.DISPLAY);
			// 替换列表标签
			htmlContent = new com.mingsoft.bbs.parser.impl.ListParser(htmlContent, listSubjects, getAppUrl(), property, false).parse();
		}
		return htmlContent;
	}
	/**
	 * 解析评论列表标签
	 * 
	 * @param htmlContent
	 *           模版内容
	 * @param curSubjectId
	 *           帖子编号
	 * @return　替换好的内容
	 */
	private String parseComlist() {
		//----------------------------解析评论列表标签----------------------------
			if(subject == null){
				return htmlContent;
			}
			String channel = htmlContent;
			// 从数据库取出文章列表数组
			List<CommentEntity>   commentList= new  ArrayList<CommentEntity>();
			// 根据帖子id获取顶级评论总数
			int commenCount = subjectCommentBiz.countByView(this.subject.getBasicId(), 0);
			
			// 查找当前模版页面拥有多少个评论列表标签
			int strNumType = CommentListParser.channelNum(channel);
			// 替换完分页标签后的HTML代码
			for (int i = 0; i < strNumType; i++) {
				// 当前列表栏目中属性集合
				Map<String, String> mapProperty = CommentListParser.channelProperty(channel);
				// 取当评论列表显示条数
				String _size= mapProperty.get(CommentListParser.LIST_SIZE);
				int size =20;
				if(!StringUtil.isBlank(_size)){
					size=StringUtil.string2Int(_size);
				}
				//获取评论列表类型
				String type = mapProperty.get(CommentListParser.COMLIST_TYPE);
				//获取排序方式
				String _order =   mapProperty.get(CommentListParser.LIST_ORDER);
				//排序方式
				boolean order = true;
				//判断是否降序
				if(!StringUtil.isBlank(_order) &&_order.equals(mapProperty.get(CommentListParser.LIST_ASC)) ){
					order=false;
				}
				// 如果没有指定评论每页显示数量则显示所有数量
				if (size <= 0 || size > commenCount) {
					size = commenCount;
				}
				if(StringUtil.isBlank(listLinkPath)){
					return htmlContent;
				}
				//分页对象
				page = new PageUtil(curPageNo, size, commenCount, listLinkPath);
				if(type.equals(CommentListParser.COMLIST_TYPE_SON)){
					//查询子评论
					for(int m =0; m<commentList.size();m++){
						CommentEntity newComment = commentList.get(m);
						if(newComment !=null){
							htmlContent = new CommentListParser(channel,newComment.getChildComment(),page).parse();
						}
					}
					channel=htmlContent;
					return htmlContent;
				}
				// 获取顶级评论
				commentList = subjectCommentBiz.queryByView(this.subject.getBasicId(), 0, page, null, order);
				//显示顶级评论
				htmlContent = new CommentListParser(channel,commentList,page).parse();
				//再次返回时调用
				channel=htmlContent;
			}
		return htmlContent;
	}
	
	/**
	 * 解析当前板块标签
	 * @return
	 */
	private String parseForum() {
		if (this.forum==null) {
			return htmlContent;
		}
		
		return new TypeParser(htmlContent,this.forum,this.getAppUrl(),(CategoryEntity)this.categoryBiz.getEntity(forum.getCategoryCategoryId())).parse();
	}
	/**
	 * 解析分页列表标签
	 * 
	 * @param htmlContent
	 *            模版内容
	 * @param column
	 *            栏目编号
	 * @param curPageNo
	 *            当前页码
	 * @return　替换好的内容
	 */
	private String parseList() {

		Map<String, String> property = ListParser.listProperty(htmlContent, true);
		if (StringUtil.isBlank(property)) { // 没有找到分页标签标签
			return htmlContent;
		}
		//获取标签下的板块id
		int categoryId=StringUtil.string2Int(property.get(ListParser.LIST_TYPEID));
		// 替换完分页标签后的HTML代码
		// 当前列表标签中属性的集合-------------------
		if(this.forum!=null){
			categoryId = forum.getCategoryId();
		}
		String isPaging = property.get(ListParser.LIST_ISPAGING);
		
		if (isPaging != null && isPaging.equals("true")) {
			// 列表每页显示的数量
			int size = StringUtil.string2Int(property.get(ListParser.LIST_SIZE));
			//推荐属性
			String flag = property.get(ListParser.LIST_FLAG);
			//不推荐属性
			String noFlag = property.get(ListParser.LIST_NOFLAG);
			
			
			
			//依据排序字段
			String orderBy = property.get(ListParser.LIST_ORDERBY);
			//排序方式
			String order = property.get(ListParser.LIST_ORDER);
			//默认是降序
			boolean _order=false;
			// 数据库中该栏目下文章的总数
			//int subjectCount = subjectBiz.countByCategoryId(app.getAppId(), categoryId,modelId,this.keyWord);
			if(!StringUtil.isBlank(order) && order.equals("asc")){
				_order=true;
			}
			int subjectCount = subjectBiz.getCountByForumIdAndSubjectTypeId(app.getAppId(), categoryId, null, flag, noFlag,SubjectEnum.DISPLAY);
			// 如果没有指定文章每页显示数量则显示所有数量
			if (size <= 0 || size > subjectCount) {
				size = subjectCount;
			}
			//if (page==null)  {
			page = new PageUtil(curPageNo, size, subjectCount, listLinkPath);
			//}
			// 从数据库取出帖子列表数组
			List<SubjectEntity> listSubjects = subjectBiz.queryByForumIdAndSubjectTypeId(app.getAppId(), categoryId,keyWord,page,flag, noFlag,orderBy,_order,SubjectEnum.DISPLAY);
			// 替换列表标签
			htmlContent = new com.mingsoft.bbs.parser.impl.ListParser(htmlContent, listSubjects, getAppUrl(), property, true).parse();
		}
		return htmlContent;
	}

	/**
	 * 解析分页标签
	 * @return
	 */
	private String parsePage() {
		// 替换分页标签
		htmlContent = new PageParser(htmlContent, page).parse();
		// 替换页面的总数，当前文章处于第几页，列表文章的总数标签
		htmlContent = new PageNumParser(htmlContent, page).parse();
		return htmlContent;
	}
	
	
	/**
	 * 论坛统计，如今日发帖、昨日发帖、用户数等
	 * @return
	 */
	private String parseStatistics(){
		//获取帖子列表的模块id
		BaseEnum bem=ModelCode.BBS_SUBJECT;
		ModelEntity model = modelbiz.getEntityByModelCode(bem);
		int modelId= model.getModelId();
		//查询论坛统计，如今日发帖、昨日发帖、用户数等
		StatisticsBean sb = this.categoryBiz.statistics(this.curCategoryId, this.app.getAppId(), modelId);
		
		//论坛今天的发帖数标签：{ms:field.total.today/}
		htmlContent = new TotalYesterdayParser(htmlContent,sb.getYestoday()).parse();
		
		//论坛昨天的发帖数：{ms:field.total.yestoday/}
		htmlContent = new TotalTodayParser(htmlContent,sb.getToday()).parse();
		
		//总帖子总数标签:{ms:field.total.subject/}
		htmlContent = new TotalSubjectParser(htmlContent,sb.getTotal()).parse();
		
		//总会员数
		htmlContent =  new TotalPeopleParser(htmlContent,sb.getPeople()).parse();

		return htmlContent;
	}
	
	
	
	
	
	
	
	/**
	 * 解析频道、栏目标签
	 * @return
	 */
	private String parseChannel() {
		// //----------------------------解析栏目标签----------------------------
		
		String channel = htmlContent;
		// 查找当前模版页面拥有多少个栏目列表标签
		int strNumType = CategoryParser.channelNum(channel);
		//获取站点id和模块id
		int appId=app.getAppId();
		//获取板块的modelId
		BaseEnum bem=ModelCode.BBS_CATEGORY;
		ModelEntity model = modelbiz.getEntityByModelCode(bem);
		int categoryModelId= model.getModelId();
		
		
		for (int i = 0; i < strNumType; i++) {
			// 当前列表栏目中属性集合
			Map<String, String> mapProperty = CategoryParser.channelProperty(channel);
			List categoryList = null;
			String forumId = mapProperty.get(ChannelParser.CHANNEL_TYPEID);
			String type = mapProperty.get(ChannelParser.CHANNEL_TYPE);
			//同级栏目是否显示属性
			String childType = mapProperty.get(ChannelParser.CHANNEL_TYP_SIBLING);
			// 判断用户填写的栏目属性，如果未填写那么取当前栏目的下级栏目，如果但前栏目没有下级栏目那么晚取本级栏目
			// 如果填写:son,那么取下级栏目，没有下级栏目则取本级栏目
			// 如果为：top,那么取上级栏目，如果没有上级栏目则取本级栏目
			// 如果为：level,则取本级栏目
			
			Integer tempColumnId= null;
			if(forum!=null){
				tempColumnId =this.forum.getCategoryId();
			}
			ForumEntity curForum = new ForumEntity();
			//如果指定了板块id
			if(!StringUtil.isBlank(forumId) && StringUtil.isInteger(forumId)){
				curForum = (ForumEntity) this.categoryBiz.getByForumId(Integer.valueOf(forumId), appId);
				if(curForum!=null){
					tempColumnId =curForum.getCategoryId();
				}
			}
			
			if (type == null || type.equals(ChannelParser.CHANNEL_TYPE_SON)) { //默认取子栏目
				categoryList = this.categoryBiz.queryForum(null,tempColumnId, appId, categoryModelId);
				//当值为true表示不存在子级分类时，显示他的同级分类
				if(childType!=null && childType.equals("true") && categoryList.size()<=0){
					if(forum.getCategoryCategoryId()!=0){
						categoryList = this.categoryBiz.querySibling(tempColumnId, appId);
					}
				}
			} else if (type.equals(ChannelParser.CHANNEL_TYPE_TOP)) {
				if (forum==null) {
					categoryList = this.categoryBiz.queryForum(null,0, appId, categoryModelId);
				} else if(forum.getCategoryCategoryId()==0) {
					categoryList = this.categoryBiz.queryForum(null,0, appId, categoryModelId);
				} else if (forum.getCategoryCategoryId()>0) {
					CategoryEntity temp = (CategoryEntity)this.categoryBiz.getEntity(forum.getCategoryCategoryId());
					categoryList = this.categoryBiz.queryForum(null,temp.getCategoryCategoryId(), appId, categoryModelId);
				}
			} else if (type.equals(ChannelParser.CHANNEL_TYPE_LEVEL)) { 
				categoryList = this.categoryBiz.queryForum(null,forum.getCategoryCategoryId(), appId, categoryModelId);
				
			}
			htmlContent = new CategoryParser(channel, categoryList,this.getAppUrl(),this.categoryBiz,forum != null ? forum.getCategoryId() : 0, mapProperty.get(ChannelParser.CHANNEL_CLASS)).parse();
			//再次返回时调用
			channel=htmlContent;
		}
		return htmlContent;
	}

	
	/**
	 * 解析当前位置
	 * @return
	 */
	private String parsePostion() {
		// //----------------------------解析栏目标签----------------------------
		
		// 查找当前模版页面拥有多少个标签
		int total = count(htmlContent,PostionParser.POSTION_BEGIN);
		for (int i = 0; i < total; i++) {
			PostionParser pp = new PostionParser(htmlContent, this.postion, this.getAppUrl());
			htmlContent = pp.parse();
		}
		return htmlContent;
	}

	
	/**
	 * 解析帖子信息 
	 */
	private String parseSubject() {
		if (subject == null) {
			return htmlContent;
		}
		htmlContent = new ContentParser(htmlContent,this.subject).parse();
		//替换帖子发布时间标签：{ms:field.date fmt="yyyy-MM-dd"/}  
		htmlContent = new DateParser(htmlContent,subject.getBasicDateTime()).parse();
		//帖子评论总数标签{ms:field.total.comment/}
		htmlContent = new TotalCommentParser(htmlContent, subject.getSubjectTotalComment()+"").parse();
		//获取当前帖子用户的发帖数
		int peopleSubjectCount =  subjectBiz.countByPeopleId(subject.getBasicAppId(), subject.getBasicPeopleId());
		//当前帖子用户的发帖总数标签{ms:field.total.subject/}
		htmlContent = new TotalPeopleSubjectParser(htmlContent, peopleSubjectCount+"").parse();	
		//当评论条数大于0时，获取最后发布评论时间，否则去发帖时间
		if(subject.getSubjectTotalComment()>0 || StringUtil.isBlank(subject.getBasicDateTime())){
			//替换评论最后回复时间：{ms:field.date.comment.last fmt=yyyy-MM-dd hh:mm:ss/}
			htmlContent = new CommentDateLastParser(htmlContent, subject.getSubjectLastCommentTime()).parse();
		}else{
			//替换评论最后回复时间：{ms:field.date.comment.last fmt=yyyy-MM-dd hh:mm:ss/}
			htmlContent = new CommentDateLastParser(htmlContent, subject.getBasicDateTime()).parse();
		}
		//替换用户id
		htmlContent = new AuthorIdParser(htmlContent,StringUtil.int2String(subject.getBasicPeopleId())).parse();
		//判断帖子信息里是否存在帖子作者信息,如果存在,解析标签,如果不存在,执行默认值
		if(subject.getSubjectPeopleUser()!=null){
			//替换发帖人昵称
			String peopleNickName =subject.getSubjectPeopleUser().getPeopleName();
			//如果发帖人昵称没有则显示发帖人帐号
			if(StringUtil.isBlank(peopleNickName)){
				peopleNickName= subject.getSubjectPeopleUser().getPuNickname();
			}
			//解析帖子作者标签
			htmlContent = new AuthorParser(htmlContent, peopleNickName).parse();
				
			//判断是否存在帖子作者头像,如果存在解析标签,不存在,执行默认值
			if(!StringUtil.isBlank(subject.getSubjectPeopleUser().getPuIcon())){
				//解析帖子作者头像标签
				htmlContent = new AuthorIconParser(htmlContent, subject.getSubjectPeopleUser().getPuIcon()).parse();
			}else{
				//执行帖子作者头像默认值
				htmlContent = new AuthorIconParser(htmlContent,AUTHOR_ICON).parse();
			}
		}
		//解析上一篇\下一篇 
		htmlContent = new PreviousNextParser(htmlContent,this.previous,this.next,this.getAppUrl()).parse();
		return htmlContent;
	}

	/**
	 * 获取appUrl地址
	 * @return
	 */
	private String getAppUrl() {
		//获取域名地址
		String link = app.getAppHostUrl()+File.separator+Const.MBBS;
		return link;
	}

}
