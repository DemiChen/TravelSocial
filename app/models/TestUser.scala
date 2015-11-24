package models

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class TestUser(
  id: Long,
  mobileNum: String,
  password: String,
  name: String,
  emailConfirmed: Boolean,
  active: Boolean)

object TestUser {
  import FakeDB.users

  def findById(id: Long): Future[Option[TestUser]] = Future.successful {
    users.get(id)
  }
  def findByEmail(email: String): Future[Option[TestUser]] = Future.successful {
    users.find(_.mobileNum == email)
  }

  def insert(email: String, password: String, name: String): Future[(Long, TestUser)] = Future.successful {
    users.insert(TestUser(_, email, password, name, emailConfirmed = false, active = false))
  }

  def update(id: Long, name: String): Future[Boolean] = Future.successful {
    users.update(id)(_.copy(name = name))
  }

  def confirmEmail(id: Long): Future[Boolean] = Future.successful {
    users.update(id)(_.copy(emailConfirmed = true, active = true))
  }

  def updatePassword(id: Long, password: String): Future[Boolean] = Future.successful {
    users.update(id)(_.copy(password = password))
  }

  def delete(id: Long): Future[Unit] = Future.successful {
    FakeDB.folders.map(f => FakeDB.tasks.delete(_.folderId == f.id))
    FakeDB.folders.delete(_.userId == id)
    users.delete(id)
  }

  def list: Future[Seq[TestUser]] = Future.successful {
    users.values.sortBy(_.name)
  }

}
