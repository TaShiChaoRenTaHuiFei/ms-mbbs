package com.mingsoft.bbs.aop;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mingsoft.base.constant.e.BaseSessionEnum;
import com.mingsoft.base.entity.SessionEntity;
import com.mingsoft.basic.biz.IAppBiz;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.bbs.entity.SubjectEntity;
import net.mingsoft.message.biz.IMessageBiz;
import net.mingsoft.message.constant.e.MessageTypeEnum;
import net.mingsoft.message.entity.MessageEntity;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.people.constant.e.SessionConstEnum;
import com.mingsoft.people.entity.PeopleUserEntity;
import com.mingsoft.util.RegexUtil;
import com.mingsoft.util.StringUtil;


/**
 * bbs切面基础方法
 * 
 * @author 史爱华
 * @version 版本号：100-000-000<br/>
 *          创建日期：2015-12-14<br/>
 *          历史修订：<br/>
 */
public abstract class BaseAop extends com.mingsoft.basic.aop.BaseAop {

	/**
	 * 注入用户业务层
	 */
	@Autowired
	private IPeopleUserBiz peopleUserBiz;

	/**
	 * 注入消息业务层
	 */
	@Autowired
	private IMessageBiz messageBiz;

	/**
	 * 保存消息实体信息
	 * 
	 * @param sendPeopleId
	 *            消息发送者id
	 * @param receivePeopleId
	 *            消息接收者id
	 * @param peopleId
	 *            对应操作帖子的用户id。
	 * @param peopleName
	 *            对应操作帖子的用户的用户名
	 * @param subject
	 *            操作的帖子的实体
	 * @param content
	 *            发送的内容模板
	 * @param type
	 *            对应操作的类型。如“delete：表示删除”
	 * @param messageType
	 */
	protected void sendMessage(int sendPeopleId, int receivePeopleId, int peopleId, SubjectEntity subject,
			String contentTemplate, MessageTypeEnum messageType, String type) {
		// 给用户发送消息
		MessageEntity message = new MessageEntity();
		String content = com.mingsoft.bbs.constant.Const.MESSAGE_RESOURCES.getString(contentTemplate);
		// 获取发送内容的模板
		if (!StringUtil.isBlank(content)) {
			PeopleUserEntity people = (PeopleUserEntity) this.peopleUserBiz.getEntity(peopleId);
			if (people != null) {
				String peopleName = people.getPeopleName();
				if (StringUtil.isBlank(peopleName)) {
					peopleName = people.getPeopleUserNickName();
				}
				content = RegexUtil.replaceAll(content, "\\{uid\\}", peopleId + "");
				// 判断用户名是否存在
				content = RegexUtil.replaceAll(content, "\\{id\\}", subject.getBasicId() + "");
				content = RegexUtil.replaceAll(content, "\\{peopleName\\}", peopleName);
				content = RegexUtil.replaceAll(content, "\\{title\\}", subject.getBasicTitle());
				if (!StringUtil.isBlank(type)) {
					// 发帖用户发送消息
					content = RegexUtil.replaceAll(content, "\\{type\\}", type);
				}
				// 消息发送给发布帖子的用户
				message.setMessageReceivePeopleId(receivePeopleId);
				message.setMessageType(messageType);
				message.setMessageContent(content);
				message.setMessageSendPeopleId(sendPeopleId);
				messageBiz.saveEntity(message);
			}

		}
	}
}
