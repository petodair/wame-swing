package br.com.wame.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wame.manager.SessionManager;
import br.com.wame.model.TimeClock;
import br.com.wame.model.dto.ApiResponse;

public class TimeClockService {
	
	private static final String BASE_URL = "http://localhost:8080/api/timeclock";
	HttpClient client;
	ObjectMapper mapper;
	
	public TimeClockService() {
		client = HttpClient.newHttpClient();
		mapper = new ObjectMapper();
	}
	
	public ApiResponse<List<TimeClock>> getByWork(Long workId) {
		ApiResponse<List<TimeClock>> apiResponse = buildError(
				"Não foi possível encontrar os trabalhos, verifique sua conexão.");
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + "/work/" + workId))
					.header("Cookie", SessionManager.getToken())
					.GET()
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			TypeReference<ApiResponse<List<TimeClock>>> typeRef = new TypeReference<>() {
			};
			apiResponse = mapper.readValue(response.body(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
	
	public ApiResponse<Void> clockNow() {
		ApiResponse<Void> apiResponse = buildError(
				"Não foi registrar o ínicio do turno, verifique sua conexão.");
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + "/clock-now"))
					.header("Cookie", SessionManager.getToken())
					.PUT(HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			TypeReference<ApiResponse<Void>> typeRef = new TypeReference<>() {
			};
			apiResponse = mapper.readValue(response.body(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
	
	// Utilitário para criar erro padronizado
		private <T> ApiResponse<T> buildError(String message) {
			return new ApiResponse<>("error", 0, null, message);
		}

}
