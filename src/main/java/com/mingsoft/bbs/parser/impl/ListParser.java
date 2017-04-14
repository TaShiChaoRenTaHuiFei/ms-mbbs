package com.mingsoft.bbs.parser.impl;


import java.util.List;
import java.util.Map;

import com.mingsoft.bbs.constant.Const;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.parser.impl.general.DateParser;
import com.mingsoft.util.DateUtil;
import com.mingsoft.util.StringUtil;

/**
 * 解析列表标签, {ms:arclist typeid= size= titlelen= flag = }:列表头标签,<br/>
 * {/ms:arclist}:列表尾标签,<br/>
 * 列表中的属性：<br/>
 * size 类型 int 返回文档列表总数,默认为20条全部返回，也可以配合分页使用,<br/>
 * titlelen 类型 int 标题长度,等同于titlelength。默认40个汉字,<br/>
 * [field.index/]:序号,根据显示条数显示的序号1 2 …..10,<br/>
 * [field.id/]:编号,对应帖子在数据库里的自动编号,<br/>
 * [field.title/]:标题,标题长度根据titlelen的属性值指定，默认40个汉字,<br/>
 * [field.fulltitle/]:全部标题,显示完整的标题,<br/>
 * [field.author/]:帖子作者,<br/>
 * [field.date fmt=yyyy-mm-dd hh:mm:ss/]:发帖时间,<br/>
 * [field.content length=/]:内容,length=:内容的长度,指定获取帖子长度(非必填),<br/>
 * [field.typename/]:分类名称，帖子所属分类的名称,<br/>
 * [field.typeid/]:分类编号,帖子所属分类的编号,<br/>
 * [field.typelink/]:分类连接,点击连接连接到当前分类的列表,<br/>
 * [field.link/]:内容链接,点击显示帖子具体的内容地址,<br/>
 * [field.date.comment.last fmt=yyyy-mm-dd hh:mm:ss/]：帖子最后回复时间：
 * [field.litpic/]：发帖作者头像
 * [field.cmtcount/]：评论总条数
 * [field.hit/]：帖子点击数
 * [field.num]：当前页面文章的数量
 * [field.subjecttype]:帖子列表标签
 * [field.descrip/]:帖子描述
 * 
 * @author 李书宇   技术支持：景德镇铭飞科技 官网：www.ming-soft.com
 */
public class ListParser extends com.mingsoft.mdiy.parser.ListParser {

	
	/**
	 * 帖子的评论总条数，帖子列表标签[field.total.comment/]
	 */
	protected final static String FIELD_TOTAL_COMMENT="\\[field.total.comment/\\]";
	
	/**
	 * 帖子的最后回复时间,帖子列表标签[field.date.comment.last fmt=yyyy-mm-dd hh:mm:ss/]
	 */
	protected final static String FIELD_DATE_COMMENT_LAST="\\[field.date.comment.last\\s{0,}(fmt=(.*?))?/]";
	
	/**
	 * 帖子点击量,帖子列表标签[field.hit/]
	 */
	protected final static String FIELD_HIT="\\[field.hit/\\]";
	
	/**
	 * 作者    帖子列表子标签 [field.author.name/]
	 */
	protected final static String FIELD_AUTHOR_NAME="\\[field.author.name/\\]";
	
	/**
	 * 帖子发布者头像，帖子列表标签[field.author.icon/]
	 */
	protected final static String FIELD_AUTHOR_ICON="\\[field.author.icon/\\]";
	
	/**
	 * 帖子发布者用户id，帖子列表标签[field.author.id/]
	 */
	protected final static String FIELD_AUTHOR_ID="\\[field.author.id/\\]";
	
	/**
	 * 帖子所属分类名 ,帖子列表标签[field.typetitle/]
	 */
	protected final static String FIELD_TYPETITLE="\\[field.typetitle/\\]";
	
	/**
	 * 帖子属性，帖子列表标签[field.subjecttype]
	 */
	protected final static String FIELD_SUBJECTTYPE="\\[field.subjecttype/\\]";
	
	/**
	 * 帖子排序，帖子列表标签[field.sort]
	 */
	protected final static String FIELD_SORT="\\[field.sort/\\]";
	
	/**
	 * 默认发帖作者为匿名
	 */
	protected final static String  AUTHOR ="<!--未找到该标签内容-->";
	
	/**
	 * 默认发帖作者头像
	 */
	protected final static String  AUTHOR_ICON ="<!--未找到该标签内容-->";
	
	/**
	 * 帖子需要显示的属性
	 */
	protected final static String FLAG="flag";
	
	/**
	 * 帖子不需要显示的属性
	 */
	protected final static String NO_FLAG="noflag";
	
	/**
	 * 将剔除标签后的内容输出
	 */
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		String channelHtml = this.replaceFirst(TAB_BODY);
		return channelHtml;
	}
	
	/**
	 * 列表解析构造,
	 * 
	 * @param htmlCotent
	 * 			原始内容
	 * @param subjects
	 * 			文章列表
	 * @param websiteUrl
	 * 			网站地址
	 * @param isPaging
	 *            ture:分页
	 * @param listProperty
	 * 			当前标签属性
	 */
	public ListParser(String htmlCotent, List<SubjectEntity> subjects,String websiteUrl, Map<String, String> listProperty, boolean isPaging) {
		String tabTmpContent = "";
		
		if (isPaging) {
			// 在HTML模版中标记出要用内容替换的标签
			tabTmpContent = replaceStartAndEnd(htmlCotent,LIST_PAGING);
		} else {
			tabTmpContent = replaceStartAndEnd(htmlCotent,LIST_NOPAGING);
		}
		this.listProperty = listProperty;
		
		
		// 经过遍历后的数组标签
		super.newCotent = list(tabTmpContent,htmlCotent,subjects, websiteUrl);
		super.htmlCotent = tabTmpContent;
		
	}
	
	
	
	/**
	 * 遍历文章数组，将取出的内容替换标签
	 * 
	 * @param tabHtmlContent
	 *            替换过的html模版
	 * @param htmlContent
	 *            原始htlm模版内容
	 * @param articleList
	 *            文章数组
	 * @param path
	 *            项目路径
	 * @return 用内容替换标签后的HTML代码
	 */
	@Override
	protected String list(String tabHtmlContent,String htmlCotent, List subjectList, String path) {
		String htmlList = "";
		String tabHtml = tabHtml(tabHtmlContent);
		
		if (subjectList != null && tabHtml != null && subjectList.size() != 0 && tabHtml != "") {
			for (int i = 0; i < subjectList.size(); i++) {
				SubjectEntity subject = (SubjectEntity)subjectList.get(i);
				// 序号,根据显示条数显示的序号1 2 …..10。
				htmlList += tabContent(tabHtml, StringUtil.int2String((i + 1)),INDEX_FIELD_LIST);
				// 编号,对应帖子在数据库里的自动编号。
				htmlList = tabContent(htmlList, StringUtil.int2String(subject.getBasicId()),ID_FIELD_LIST);
				// 标题,标题长度根据titlelen的属性值指定，默认40个汉字,
//				htmlList += tabContent(tabHtml, titleLength(subject.getBasicTitle(), htmlContent),TITLE_FIELD_LIST);
				// 全部标题,显示完整的标题。[field.people.name/]
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getBasicTitle()),TITLE_FIELD_LIST);
				//判断帖子作者信息是否存在,如果存在,执行解析,不存在显示默认值
				if(subject.getSubjectPeopleUser()!=null){
					//替换发帖人昵称
					String peopleNickName =subject.getSubjectPeopleUser().getPeopleName();
					//如果发帖人昵称没有则显示发帖人帐号
					if(StringUtil.isBlank(peopleNickName)){
						peopleNickName= subject.getSubjectPeopleUser().getPeopleUserNickName();
					}
					// 帖子作者。[field.people.name/]
					htmlList = tabContent(htmlList, StringUtil.null2String(peopleNickName),FIELD_AUTHOR_NAME);
					//判断发帖作者头像是否存在,如果存在,解析标签,如果不存在,执行默认值
					if(!StringUtil.isBlank(subject.getSubjectPeopleUser().getPeopleUserIcon())){
						// 用户头像[field.people.icon/]
						htmlList = tabContent(htmlList, StringUtil.null2String(subject.getSubjectPeopleUser().getPeopleUserIcon()),FIELD_AUTHOR_ICON);
					}else{
						htmlList = tabContent(htmlList, StringUtil.null2String(AUTHOR_ICON),FIELD_AUTHOR_ICON);
					}
				}
				
				
				// 帖子发布时间(非必填),
				htmlList = new DateParser(htmlList,subject.getBasicDateTime()).parse();
				// 帖子内容 [field.content length=/]
				htmlList =  tabContent(htmlList, contentLength(subject.getSubjectContent(), htmlList),CONTENT_FIELD_LIST);
				// 帖子所属分类名称  [field.typetitle/]
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getSubjectForum().getCategoryTitle()+""),FIELD_TYPETITLE);
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getBasicPeopleId()+""),FIELD_AUTHOR_ID);
				//获取评论条数
				int subjectCountComment = subject.getSubjectTotalComment();
				//判断评论条数是否大于0，如果大于0显示标签内容，如果小于等于0，显示评论总条数为0，最新评论时间为暂无评论
				if(subjectCountComment>0){
					// 帖子评论总数[field.total.comment/]
					htmlList = tabContent(htmlList, StringUtil.null2String(subjectCountComment+""),FIELD_TOTAL_COMMENT);
					htmlList = tabContent(htmlList,DateUtil.pastTime(subject.getSubjectLastCommentTime()),FIELD_DATE_COMMENT_LAST);
					
				}else{
					// 帖子评论总数[field.total.comment/]
					htmlList = tabContent(htmlList, StringUtil.null2String(0+""),FIELD_TOTAL_COMMENT);
					// 帖子最后回复时间[field.date.comment.last fmt=yyyy-mm-dd hh:mm:ss/]
					htmlList = tabContent(htmlList,DateUtil.pastTime(subject.getBasicDateTime()),FIELD_DATE_COMMENT_LAST);
				}
				// 帖子点击量[field.hit/]
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getBasicHit()+""),FIELD_HIT);
				
				// 帖子排序[field.sort/]
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getBasicSort()+""),FIELD_SORT);
				// 帖子链接 ：[field.link/]
				String link = StringUtil.buildPath(path,subject.getBasicId(),Const.DETAIL+DO_SUFFIX) .substring(1);
				htmlList = tabContent(htmlList, link,LINK_FIELD_LIST);
				
				// 分类编号,帖子所属分类的编号,
				htmlList = tabContent(htmlList, subject.getBasicCategoryId(),TYPEID_FIELD_LIST);
				
				// 帖子属性，[field.subjecttype]
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getBasicType()),FIELD_SUBJECTTYPE);
				
				// 当前页面文章的数量[field.num]
				String numArticle = Integer.toString(subjectList.size());
				htmlList = tabContent(htmlList, numArticle,NUM_ARTICLE_LIST);
				//分类连接：[field.typelink/]	点击连接连接到当前分类的列表
				String channelLink =   StringUtil.buildPath(path,subject.getBasicCategoryId(),Const.LIST+DO_SUFFIX) .substring(1); 
				htmlList = tabContent(htmlList, channelLink,TTYPELINK_FIELD_LIST);
				
				/**
				 *  帖子描述标签帖子列表子标签[field.descrip/]
				 */
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getBasicDescription()),DESCIRIP_FIELD_LIST);
				
				/**
				 *   帖子缩略图  帖子列表子标签 [field.litpic/]
				 */
				htmlList = tabContent(htmlList, StringUtil.null2String(subject.getBasicThumbnails()),LITPIC_FIELD_LIST);
			}
		}
		return htmlList;
	}
	
	

}
