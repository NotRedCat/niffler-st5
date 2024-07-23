package guru.qa.niffler.api;

import guru.qa.niffler.model.*;

import java.io.IOException;
import java.util.List;

public class GatewayApiClient extends ApiClient {
    private final GatewayApi gatewayApi;

    public GatewayApiClient() {
        super(CFG.spendUrl());
        this.gatewayApi = retrofit.create(GatewayApi.class);
    }

    public List<SpendJson> getSpends(String bearerToken) throws IOException {
        return gatewayApi.getSpends(bearerToken)
                .execute()
                .body();
    }

    public List<CategoryJson> getCategories(String bearerToken) throws IOException {
        return gatewayApi.getCategories(bearerToken)
                .execute()
                .body();
    }

    public CategoryJson addCategory(String bearerToken, CategoryJson categoryJson) throws IOException {
        return gatewayApi.addCategory(bearerToken, categoryJson)
                .execute()
                .body();
    }

    public List<CurrencyJson> getAllCurrencies(String bearerToken) throws IOException {
        return gatewayApi.getAllCurrencies(bearerToken)
                .execute()
                .body();
    }

    public UserJson currentUser(String bearerToken) throws IOException {
        return gatewayApi.currentUser(bearerToken)
                .execute()
                .body();
    }

    public List<UserJson> friends(String bearerToken) throws IOException {
        return gatewayApi.friends(bearerToken)
                .execute()
                .body();
    }

    public void removeFriend(String bearerToken, String username) throws IOException {
        gatewayApi.removeFriend(bearerToken, username).execute();
    }

    public List<UserJson> incomeInvitations(String bearerToken) throws IOException {
        return gatewayApi.incomeInvitations(bearerToken)
                .execute()
                .body();
    }

    public List<UserJson> outcomeInvitations(String bearerToken) throws IOException {
        return gatewayApi.outcomeInvitations(bearerToken)
                .execute()
                .body();
    }

    public UserJson sendInvitation(String bearerToken, FriendJson friend) throws IOException {
        return gatewayApi.sendInvitation(bearerToken, friend)
                .execute()
                .body();
    }

    public UserJson acceptInvitation(String bearerToken, FriendJson friend) throws IOException {
        return gatewayApi.acceptInvitation(bearerToken, friend)
                .execute()
                .body();
    }

    public UserJson declineInvitation(String bearerToken, FriendJson friend) throws IOException {
        return gatewayApi.declineInvitation(bearerToken, friend)
                .execute()
                .body();
    }

    public List<UserJson> allUsers(String bearerToken) throws IOException {
        return gatewayApi.allUsers(bearerToken)
                .execute()
                .body();
    }

    public SpendJson addSpend(String bearerToken, SpendJson spendJson) throws IOException {
        return gatewayApi.addSpend(bearerToken, spendJson)
                .execute()
                .body();
    }

    public void deleteSpends(String bearerToken, List<String> ids) throws IOException {
        gatewayApi.deleteSpends(bearerToken, ids).execute();
    }

    public SpendJson editSpend(String bearerToken, SpendJson spendJson) throws IOException {
        return gatewayApi.editSpend(bearerToken, spendJson)
                .execute()
                .body();
    }

    public UserJson updateUserInfo(String bearerToken, UserJson userJson) throws IOException {
        return gatewayApi.updateUserInfo(bearerToken, userJson)
                .execute()
                .body();
    }
}
