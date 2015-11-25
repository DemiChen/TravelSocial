package repositories

import javax.inject.Inject

import org.joda.time.DateTime
import play.api.db.slick.{ HasDatabaseConfigProvider, DatabaseConfigProvider }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import models._

import scala.concurrent.Future

/**
 * Created by liaoshifu on 2015/11/25
 */
class UserRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  import com.github.tototoshi.slick.MySQLJodaSupport._

  private val Users = TableQuery[UsersTable]

  def all(): Future[Seq[User]] = db.run(Users.result)

  def insert(user: User): Future[User] = ???

  private class UsersTable(tag: Tag) extends Table[User](tag, "USER") {
    def mobileNum = column[String]("mobileNum")

    def password = column[String]("password")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def userName = column[String]("userName")

    def nickName = column[String]("nickName")

    def birthday = column[DateTime]("birthday")

    def gender = column[String]("gender")

    def area = column[String]("area")

    def career = column[String]("career")

    def school = column[String]("school")

    def travelSignature = column[String]("travelSignature")

    def personalSignature = column[String]("personalSignature")

    def avatarUrl = column[String]("avatarUrl")

    def createAt = column[DateTime]("createAt")

    def email = column[String]("email")

    def available = column[Boolean]("available")

    def * = (mobileNum, password, id, userName.?, nickName.?, birthday.?, gender.?, area.?, career.?, school.?,
      travelSignature.?, personalSignature.?, avatarUrl.?, createAt, email.?, available) <> (User.tupled, User.unapply _)
  }
}
