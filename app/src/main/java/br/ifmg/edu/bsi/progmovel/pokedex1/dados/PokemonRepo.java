package br.ifmg.edu.bsi.progmovel.pokedex1.dados;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Evolucao;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.EvolutionChainCompleta;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Pokeapi;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Pokemon;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.PokemonSpecies;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonRepo {

    private Pokeapi api;

    public PokemonRepo(Pokeapi api) {
        this.api = api;
    }

    public Pokemon buscar(String nome) throws IOException {
        Pokemon p = api.fetch(nome).execute().body();
        return p;
    }

    public List<Pokemon> getCadeiaEvolutiva(String nome) throws IOException{
        Pokemon original = buscar(nome);
        String[] url = original.species.url.split("/");
        int speciesId = Integer.valueOf(url[url.length-1]);

        PokemonSpecies species = api.getSpecies(speciesId).execute().body();

        url = species.evolution_chain.url.split("/");
        int chainId = Integer.valueOf(url[url.length-1]);

        EvolutionChainCompleta chain = api.getEvolutionChain(chainId).execute().body();

        List<Pokemon> resultado = new LinkedList<>();

        Deque<Evolucao> pilha = new LinkedList<>();
        pilha.push(chain.chain);
        while (!pilha.isEmpty()){
            Evolucao e = pilha.pop();
            Pokemon p = buscar(e.species.name);
            resultado.add(p);

            for(int i = 0; i<e.envolves_to.length;i++){
                pilha.push(e.envolves_to[i]);
            }
        }
        return resultado;
    }
}
