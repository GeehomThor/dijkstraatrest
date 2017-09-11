package com.github.dijkstra.dao.proxy.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.hk2.api.Factory;

public class JerseyClientFactory implements Factory<Client> {

    @Override
    public Client provide() {
        return ClientBuilder.newBuilder().build();
    }

    @Override
    public void dispose(Client instance) {
        instance.close();
    }

}
