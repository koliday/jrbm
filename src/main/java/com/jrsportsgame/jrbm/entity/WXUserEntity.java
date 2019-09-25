package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信服务器返回的用户信息封装类
 */
@Getter
@Setter
@ToString
public class WXUserEntity {


	// 用户的唯一标识
	private String openid;
	// 用户的昵称
	private String nickname;
	// 用户的性别
	private String sex;
	// 用户所在的省份
	private String province;
	// 用户所在的城市
	private String city;
	// 用户所在的国家
	private String country;
	// 用户的头像URL地址
	private String headimgurl;

}
