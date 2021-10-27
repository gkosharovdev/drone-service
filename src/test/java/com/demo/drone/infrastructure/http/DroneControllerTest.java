package com.demo.drone.infrastructure.http;

import com.demo.drone.application.DroneApplicationService;
import com.demo.drone.application.DroneApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(DroneController.class)
@Import(DroneApplicationServiceImpl.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DroneControllerTest {

  private static final String DRONE_NAME = "TEST_DRONE";
  private static final com.demo.drone.infrastructure.http.Direction DRONE_DEPLOY_DIRECTION =
      com.demo.drone.infrastructure.http.Direction.NORTH;
  private static final com.demo.drone.infrastructure.http.Position DRONE_DEPLOY_POSITION =
      com.demo.drone.infrastructure.http.Position.of(5, 5);

  @Autowired private MockMvc mockMvc;

  @MockBean private DroneApplicationService droneApplicationService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void should_not_get_drone_without_name() throws Exception {
    this.mockMvc.perform(get("/v1/drones")).andDo(print()).andExpect(status().is4xxClientError());
  }

  @Test
  void should_get_drone_by_name() throws Exception {
    var drone = DroneDto.of(DRONE_NAME, DRONE_DEPLOY_POSITION, DRONE_DEPLOY_DIRECTION);
    when(droneApplicationService.findByName(any())).thenReturn(Optional.of(drone));
    mockMvc
        .perform(get("/v1/drones/TEST_DRONE"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", equalTo(DRONE_NAME)))
        .andExpect(jsonPath("$.position.x", equalTo(DRONE_DEPLOY_POSITION.getX())))
        .andExpect(jsonPath("$.position.y", equalTo(DRONE_DEPLOY_POSITION.getY())))
        .andExpect(jsonPath("$.direction", equalTo(DRONE_DEPLOY_DIRECTION.name())));
  }

  @Test
  void should_deploy_drone() throws Exception {
    var drone = DroneDto.of(DRONE_NAME, DRONE_DEPLOY_POSITION, DRONE_DEPLOY_DIRECTION);
    when(droneApplicationService.deployDrone(any())).thenReturn(drone);
    mockMvc
        .perform(
            post("/v1/drones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        DeployDroneRequest.of(DRONE_NAME, 5, 5, "NORTH", Position.of(3, 3)))))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", equalTo(DRONE_NAME)))
        .andExpect(jsonPath("$.position.x", equalTo(DRONE_DEPLOY_POSITION.getX())))
        .andExpect(jsonPath("$.position.y", equalTo(DRONE_DEPLOY_POSITION.getY())))
        .andExpect(jsonPath("$.direction", equalTo(DRONE_DEPLOY_DIRECTION.name())));
  }

  @Test
  void given_drone_exists_should_move_drone_forward() throws Exception {
    var drone = DroneDto.of(DRONE_NAME, DRONE_DEPLOY_POSITION, DRONE_DEPLOY_DIRECTION);
    when(droneApplicationService.move(any())).thenReturn(Optional.of(drone));
    mockMvc
        .perform(
            post("/v1/drones/TEST_DRONE/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        MoveRequest.of(DRONE_NAME, ZonedDateTime.parse("2021-10-21T11:00:01Z")))))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", equalTo(DRONE_NAME)))
        .andExpect(jsonPath("$.position.x", equalTo(DRONE_DEPLOY_POSITION.getX())))
        .andExpect(jsonPath("$.position.y", equalTo(DRONE_DEPLOY_POSITION.getY())))
        .andExpect(jsonPath("$.direction", equalTo(DRONE_DEPLOY_DIRECTION.name())));
  }

  @Test
  void given_drone_exists_should_turn_drone() throws Exception {
    var drone = DroneDto.of(DRONE_NAME, DRONE_DEPLOY_POSITION, DRONE_DEPLOY_DIRECTION);
    when(droneApplicationService.turn(any())).thenReturn(Optional.of(drone));
    mockMvc
        .perform(
            post("/v1/drones/TEST_DRONE/turn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        TurnRequest.of(DRONE_NAME, TurnType.RIGHT, ZonedDateTime.parse("2021-10-21T11:00:01Z")))))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", equalTo(DRONE_NAME)))
        .andExpect(jsonPath("$.position.x", equalTo(DRONE_DEPLOY_POSITION.getX())))
        .andExpect(jsonPath("$.position.y", equalTo(DRONE_DEPLOY_POSITION.getY())))
        .andExpect(jsonPath("$.direction", equalTo(DRONE_DEPLOY_DIRECTION.name())));
  }

  @Test
  void given_drone_does_not_exist_should_return_not_found_on_turn() throws Exception {
    when(droneApplicationService.turn(any())).thenReturn(Optional.empty());
    mockMvc
        .perform(
            post("/v1/drones/TEST_DRONE/turn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        TurnRequest.of(DRONE_NAME, TurnType.RIGHT, ZonedDateTime.parse("2021-10-21T11:00:01Z")))))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
  @Test
  void given_drone_does_not_exist_should_return_not_found_on_move() throws Exception {
    when(droneApplicationService.turn(any())).thenReturn(Optional.empty());
    mockMvc
        .perform(
            post("/v1/drones/TEST_DRONE/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        MoveRequest.of(DRONE_NAME, ZonedDateTime.parse("2021-10-21T11:00:01Z")))))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
