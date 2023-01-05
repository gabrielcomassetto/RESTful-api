package webservices.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import webservices.model.domain.Produto;

public class ProdutoDAO {
	
		//Listar todos
		public List<Produto> getAll(){
			EntityManager em = JPAUtil.getEntityManager();
			List<Produto> produtos = null;
			
			try {
				produtos = em.createQuery("select p from Produto p", Produto.class).getResultList();
			} finally {
			em.close();
			}
			return produtos;
		}
		
		//Listar por id
		public Produto getById(long id) {
			EntityManager em = JPAUtil.getEntityManager();
			Produto produto = null;
			
			try {
				produto = em.find(Produto.class, id);
				
			} finally {
				em.close();
			}
			return produto;
		}
		
		//Adicionar produto
		public Produto save(Produto produto) {
			EntityManager em = JPAUtil.getEntityManager();
			
			try {
				em.getTransaction().begin();
				em.persist(produto);
				em.getTransaction().commit();
			} finally {
				em.close();
			}
			return produto;
		}
		
		//Atualizar produto
		public Produto update(Produto produto) {
			EntityManager em = JPAUtil.getEntityManager();
			Produto produtoManaged = null;
			
			try {
				em.getTransaction();
				produtoManaged = em.find(Produto.class, produto.getId());
				produtoManaged.setNome(produto.getNome());
				produtoManaged.setQuantidade(produto.getQuantidade());
				em.getTransaction().commit();
			} finally {
				em.close();
			}
			return produtoManaged;
		}
		
		//Deletar produto
		public Produto delete(long id) {
			EntityManager em = JPAUtil.getEntityManager();
			Produto produto = null;
			
			try {
				em.getTransaction().begin();
				produto = em.find(Produto.class, id);
				em.remove(produto);
				em.getTransaction().commit();
				
			} finally {
				em.close();
			}
			return produto;
		}
}
