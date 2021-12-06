package dev.jakal.codechallenge.infrastructure.common.network

import dev.jakal.codechallenge.infrastructure.common.network.model.ScheduleEventNetwork
import dev.jakal.codechallenge.infrastructure.common.network.model.VideoEventNetwork
import retrofit2.http.GET

interface CodeChallengeApi {

    @GET("getEvents")
    suspend fun getEvents(): List<VideoEventNetwork>

    @GET("getSchedule")
    suspend fun getSchedule(): List<ScheduleEventNetwork>
}
