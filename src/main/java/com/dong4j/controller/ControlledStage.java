package com.dong4j.controller;

import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: dong4j.
 * Date: 2016-12-27.
 * Time: 09:31.
 * Description:
 */
public interface ControlledStage {
    Map<String, Parent> parentMap = new HashMap<>();
    void setStageController(StageController stageController);
}
