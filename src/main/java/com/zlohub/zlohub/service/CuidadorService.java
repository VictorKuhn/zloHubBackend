package com.zlohub.zlohub.service;

import com.zlohub.zlohub.model.Cuidador;
import com.zlohub.zlohub.repository.CuidadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuidadorService {

    @Autowired
    private CuidadorRepository cuidadorRepository;

    public Cuidador salvarCuidador(Cuidador cuidador) {
        return cuidadorRepository.save(cuidador);
    }

    public List<Cuidador> listarCuidadores() {
        return cuidadorRepository.findAll();
    }

    public Optional<Cuidador> buscarPorId(Long id) {
        return cuidadorRepository.findById(id);
    }

    public Cuidador atualizarCuidador(Long id, Cuidador dadosAtualizados) {
        return cuidadorRepository.findById(id)
                .map(cuidador -> {
                    cuidador.setNome(dadosAtualizados.getNome());
                    cuidador.setSobrenome(dadosAtualizados.getSobrenome());
                    cuidador.setDataNascimento(dadosAtualizados.getDataNascimento());
                    cuidador.setLogradouro(dadosAtualizados.getLogradouro());
                    cuidador.setNumero(dadosAtualizados.getNumero());
                    cuidador.setBairro(dadosAtualizados.getBairro());
                    cuidador.setCidade(dadosAtualizados.getCidade());
                    cuidador.setEstado(dadosAtualizados.getEstado());
                    cuidador.setCep(dadosAtualizados.getCep());
                    cuidador.setComplemento(dadosAtualizados.getComplemento());
                    cuidador.setTelefoneContato1(dadosAtualizados.getTelefoneContato1());
                    cuidador.setTelefoneContato2(dadosAtualizados.getTelefoneContato2());
                    cuidador.setEmailContato(dadosAtualizados.getEmailContato());
                    cuidador.setTempoExperiencia(dadosAtualizados.getTempoExperiencia());
                    cuidador.setFormacao(dadosAtualizados.getFormacao());
                    cuidador.setHabilidades(dadosAtualizados.getHabilidades());
                    cuidador.setReferencia1(dadosAtualizados.getReferencia1());
                    cuidador.setTelefoneReferencia1(dadosAtualizados.getTelefoneReferencia1());
                    cuidador.setReferencia2(dadosAtualizados.getReferencia2());
                    cuidador.setTelefoneReferencia2(dadosAtualizados.getTelefoneReferencia2());
                    cuidador.setReferencia3(dadosAtualizados.getReferencia3());
                    cuidador.setTelefoneReferencia3(dadosAtualizados.getTelefoneReferencia3());
                    cuidador.setPoliticaAceita(dadosAtualizados.getPoliticaAceita());
                    return cuidadorRepository.save(cuidador);
                })
                .orElseThrow(() -> new RuntimeException("Cuidador não encontrado"));
    }

    public void excluirCuidador(Long id) {
        cuidadorRepository.deleteById(id);
    }
}
