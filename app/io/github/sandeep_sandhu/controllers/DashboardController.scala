/**
  * File: MyController.scala
  */

package io.github.sandeep_sandhu.controllers

import io.github.sandeep_sandhu.models.StatusItem
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject._
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import play.api.libs.json._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer;

@Singleton
class DashboardController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController {

  private val todoList: ListBuffer[StatusItem] =
    new mutable.ListBuffer[StatusItem]()
  implicit val todoListJson: OFormat[StatusItem] = Json.format[StatusItem]

  todoList += StatusItem(1, "test", true)
  todoList += StatusItem(2, "some other value", false)

  def getAll(): Action[AnyContent] = Action {
    if (todoList.isEmpty) {
      NoContent
    } else {
      //getRedisItem("mycounter")
      Ok(Json.toJson(todoList))
    }
  }

  def connectToRedis(): Jedis = {
    val pool = new JedisPool("localhost", 6379);
    pool.getResource()
  }

  def getRedisItem(jedis: Jedis, key: String): Unit =
    try {
      jedis.incrBy(key, 1L)
      val counter = jedis.get(key);
      println(s"Redis counter value is = $counter")
    } catch {
      case e: Exception => e.printStackTrace()
    }

  def getByLocId(locationId: Long): Action[AnyContent] = Action {
    val foundItem = todoList.find(_.id == locationId)
    foundItem match {
      case Some(item) => Ok(Json.toJson(item))
      case None       => NotFound
    }
  }

  def getByAccNo(accountNo: Long): Action[AnyContent] = Action {
    val foundItem = todoList.find(_.id == accountNo)
    foundItem match {
      case Some(item) => Ok(Json.toJson(item))
      case None       => NotFound
    }
  }
}
