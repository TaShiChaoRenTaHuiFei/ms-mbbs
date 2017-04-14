package com.mingsoft.bbs.parser.impl;

import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.parser.IParser;

/**
 * 
 * <p>
 * <b>铭飞-BBS论坛平台</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author 李书宇
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 * @ClassName: SubjectParser
 *
 * @Description: 当前帖子解析器
 *
 *               <p>
 *               Comments: 继承IParser解析器
 *               </p>
 * 
 *               <p>
 *               Creatr Date:2015-5-2 上午9:19:41
 *               </p>
 *
 * 
 *               1.帖子内容标题(单标签)标签{ms:field.content/} 2.帖子点击量{ms:field.hit/}
 *               3.帖子编号{ms:field.id/} 4.帖子标题标签{ms:field.title/}
 *               5.帖子缩略图{ms:field.litpic/} 6.
 */
public class ContentParser extends IParser {

	/**
	 * 帖子内容标题(单标签)标签
	 */
	private final static String SUBJECT_CONTENT_FIELD = "\\{ms:field.content/\\}";

	/**
	 * 帖子点击量
	 */
	private final static String SUBJE_HIT = "\\{ms:field.hit/\\}";

	/**
	 * 帖子编号
	 */
	private final static String SUBJECT_ID = "\\{ms:field.id/\\}";

	/**
	 * 帖子标题标签
	 */
	private final static String SUBJECT_TITLE_FIELD = "\\{ms:field.title/\\}";

	/**
	 * 帖子属性
	 */
	private final static String SUBJECT_SUBJECTTYPE_FIELD = "\\{ms:field.subjecttype/\\}";

	/**
	 * 文章缩略标签
	 */
	private final static String SUBJECT_LITPIC_FIELD = "\\{ms:field.litpic/\\}";

	/**
	 * 帖子排序
	 */
	private final static String SUBJECT_SORT_FIELD = "\\{ms:field.sort/\\}";

	/**
	 * 帖子实体
	 */
	private SubjectEntity subject;

	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public ContentParser(String htmlContent, SubjectEntity subject) {
		super.htmlCotent = htmlContent;
		this.subject = subject;
	}

	@Override
	public String parse() {
		super.newCotent = subject.getSubjectContent();
		htmlCotent = super.replaceAll(SUBJECT_CONTENT_FIELD);
		super.newCotent = subject.getBasicHit() + "";
		htmlCotent = super.replaceAll(SUBJE_HIT);
		super.newCotent = subject.getBasicId() + "";
		htmlCotent = super.replaceAll(SUBJECT_ID);
		super.newCotent = subject.getBasicTitle();
		htmlCotent = super.replaceAll(SUBJECT_TITLE_FIELD);
		super.newCotent = subject.getBasicType();
		htmlCotent = super.replaceAll(SUBJECT_SUBJECTTYPE_FIELD);
		super.newCotent = subject.getBasicThumbnails();
		htmlCotent = super.replaceAll(SUBJECT_LITPIC_FIELD);
		super.newCotent = subject.getBasicSort() + "";
		htmlCotent = super.replaceAll(SUBJECT_SORT_FIELD);
		return htmlCotent;
	}

}
