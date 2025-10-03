package br.com.wame.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wame.manager.SessionManager;
import br.com.wame.model.dto.ApiResponse;
import br.com.wame.model.dto.LoginRequest;

public class AuthService {

	private static final String AUTH_URL = "http://localhost:8080/api/auth";
	HttpClient client;
	ObjectMapper mapper;

	public AuthService() {
		client = HttpClient.newHttpClient();
		mapper = new ObjectMapper();
	}

	public ApiResponse<Void> auth(String username, String password) {
		LoginRequest loginRequest = new LoginRequest(username, password);
		ApiResponse<Void> body = new ApiResponse<>("error", 0, null, "Resposta inesperada do servidor");
		try {
			String jsonString = mapper.writeValueAsString(loginRequest);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(AUTH_URL))
					.header("Content-Type", "application/json")
					.POST(BodyPublishers.ofString(jsonString))
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if(response.statusCode() == 201) {
				String setCookieHeader = response.headers().firstValue("Set-Cookie").get();
				if (setCookieHeader != null) {
					SessionManager.setToken(setCookieHeader);
				}
			}
			TypeReference<ApiResponse<Void>> typeRef = new TypeReference<>(){};
			body = mapper.readValue(response.body(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public ApiResponse<Void> logout() {
		ApiResponse<Void> body = new ApiResponse<>("error", 0, null, "Resposta inesperada do servidor");
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(AUTH_URL + "/logout"))
					.header("Content-Type", "application/json")
					.POST(BodyPublishers.noBody())
					.build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			SessionManager.setToken(null);
			TypeReference<ApiResponse<Void>> typeRef = new TypeReference<>(){};
			body = mapper.readValue(response.body(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return body;
	}
}
