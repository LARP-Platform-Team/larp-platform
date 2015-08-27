package ru.srms.larp.platform

import org.springframework.security.acls.model.NotFoundException
import ru.srms.larp.platform.game.Game

/**
 * Controller to handler game modules.
 * Throws an exception if related modules is not added to the game.
 */
abstract class BaseModuleController extends BaseController {

  /**
   * @return Game module, related to the controller
   */
  protected abstract Game.GameModule module()

  /**
   * Checks if related module is added to the current game.
   * If it is - performs the action, else throws a NotFoundException
   * @param action any action to performgi
   * @return action result
   */
  protected def withModule(Closure action) {
    if (params.game && ((Game) params.game).modules.contains(module()))
      action()
    else
      throw new NotFoundException("Module ${module()} is not available")
  }

}

