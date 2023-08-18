package br.ifmg.edu.bsi.progmovel.pokedex1.apimodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Pokeapi {
    @GET("pokemon/{name}")
    Call<Pokemon> fetch(@Path("name") String name);

    @GET("pokemon-species/{id}")
    Call<PokemonSpecies> getSpecies(@Path("id")int speciesId);

    @GET("evolution-chain/{id}")
    Call<EvolutionChainCompleta> getEvolutionChain(@Path("id")int chainId);
}
