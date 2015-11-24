package models

import org.joda.time.DateTime

case class User(
  mobileNum: String,
  password: String,
  id: Option[String],
  userName: Option[String] = None,
  nickName: Option[String] = None,
  birthday: Option[DateTime] = None,
  gender: Option[String] = None,
  area: Option[String] = None,
  career: Option[String] = None,
  school: Option[String] = None,
  travelSignature: Option[String] = None,
  personalSignature: Option[String] = None,
  avatarUrl: Option[String] = None,
  createAt: DateTime = DateTime.now(),
  email: Option[String] = None,
  available: Boolean = true)

// TODO 增加slick支持

