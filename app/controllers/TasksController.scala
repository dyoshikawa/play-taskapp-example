package controllers

import javax.inject._
import play.api.libs.circe.Circe
import play.api.mvc._
import io.circe.generic.auto._
import io.circe.syntax._
import scalikejdbc._

case class CreateTask(content: String)

case class Task(id: Int, content: String)

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class TasksController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe {
  implicit val session = AutoSession

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    val tasks = sql"select * from tasks".toMap.list.apply
    val res = tasks.map { task =>
      Task(
        task.get("id") match {
          case Some(id) => id.toString.toInt
        },
        task.get("content") match {
          case Some(content) => content.toString
        }
      )
    }
    Ok(res.asJson)
  }

  def store = Action(circe.tolerantJson[CreateTask]) { req =>
    val content = req.body.content

    sql"insert into tasks (content) values ($content)".update.apply

    Ok(req.body.asJson)
  }

  def update(id: Long) = Action(circe.tolerantJson[CreateTask]) { req =>
    val content = req.body.content

    sql"update tasks (content) values ($content) where id = $id".update.apply

    val task: Option[Task] = sql"select * from tasks where id = $id".toMap.single.apply.map { task =>
      Task(
        task.get("id") match {
          case Some(a) => a.toString.toInt
        },
        task.get("content") match {
          case Some(a) => a.toString
        }
      )
    }

    Ok(task.asJson)
  }

  def destroy(id: Long) = Action {
    sql"delete from tasks where id = $id".update.apply
    Ok(Map[String, String]("message" -> "Deleted.").asJson)
  }
}
