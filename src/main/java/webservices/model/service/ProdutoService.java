package webservices.model.service;

import java.util.List;

import webservices.model.dao.ProdutoDAO;
import webservices.model.domain.Produto;

public class ProdutoService {

		ProdutoDAO dao = new ProdutoDAO();
		
		public List<Produto> getProdutos(){
			return dao.getAll();
		}
		
		public Produto getProduto(Long id) {
			return dao.getById(id);
		}
		
		public Produto saveProduto(Produto produto) {
			return dao.save(produto);
		}
		
		public Produto updateProduto(Produto produto) {
			return dao.update(produto);
		}
		
		public Produto deleteProduto(Long id) {
			return dao.delete(id);
		}
		
}
