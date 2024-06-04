package estg.ipvc.projetoweb.Services.impl;

import estg.ipvc.projeto.data.BLL.ClientBLL;
import estg.ipvc.projeto.data.Entity.Cliente;
import estg.ipvc.projeto.data.Entity.Utilizador;
import estg.ipvc.projetoweb.Services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public List<Cliente> getAllClients() {
        List<Cliente> clientes = ClientBLL.listarClientes();
        return clientes.stream().map(cliente -> mapToClienteDto(cliente)).collect(Collectors.toList());
    }

private Cliente mapToClienteDto(Cliente cliente) {
    Cliente clienteDto = new Cliente();
    Utilizador userDto = new Utilizador();
    Utilizador user = cliente.getUtilizador();

    userDto.setIdUser(user.getIdUser());
    userDto.setUsername(user.getUsername());
    userDto.setPassword(user.getPassword());
    userDto.setNome(user.getNome());
    userDto.setRua(user.getRua());
    userDto.setNumporta(user.getNumporta());
    userDto.setCodpostal(user.getCodpostal());
    userDto.setTelefone(user.getTelefone());
    userDto.setEmail(user.getEmail());


    clienteDto.setIdCliente(cliente.getIdCliente());
    clienteDto.setUtilizador(userDto);
    clienteDto.setNif(cliente.getNif());
    clienteDto.setNomeEmpresa(cliente.getNomeEmpresa());
    clienteDto.setVendas(cliente.getVendas());

    return clienteDto;
    }

}
