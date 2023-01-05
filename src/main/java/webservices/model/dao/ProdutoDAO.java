package webservices.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import webservices.model.domain.Produto;
import webservices.model.exceptions.DAOException;
import webservices.model.exceptions.ErrorCode;

public class ProdutoDAO {
	
		//Listar todos
		public List<Produto> getAll(){
			EntityManager em = JPAUtil.getEntityManager();
			List<Produto> produtos = null;
			
			try {
				produtos = em.createQuery("select p from Produto p", Produto.class).getResultList();
			}catch(RuntimeException ex){
				throw new DAOException("Erro na listagem dos produtos do banco: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
			}
			finally {
			em.close();
			}
			return produtos;
		}
		
		//Listar por id
		public Produto getById(long id) {
			EntityManager em = JPAUtil.getEntityManager();
			Produto produto = null;
			
			if(id <= 0) {
				throw new DAOException("O id preisa ser maior que 0.", ErrorCode.BAD_REQUEST.getCode());
			}
			
			try {
				produto = em.find(Produto.class, id);
				
			}catch(RuntimeException ex) {
				throw new DAOException ("Erro ao buscar produto por id: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
			}
			
			finally {
				em.close();
			}
			
			if(produto == null) {
				throw new DAOException("O id do produto não existe." + id, ErrorCode.NOT_FOUND.getCode());
			}
			return produto;
		}
		
		//Adicionar produto
		public Produto save(Produto produto) {
			EntityManager em = JPAUtil.getEntityManager();
			
			if(!produtoIsValid(produto)) {
				throw new DAOException ("Produto com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
			}
			
			try {
				em.getTransaction().begin();
				em.persist(produto);
				em.getTransaction().commit();
			} catch(RuntimeException ex) {
				em.getTransaction().rollback();
				throw new DAOException ("Erro ao tentar salvar produto: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode() );
			}
			
			finally {
				em.close();
			}
			return produto;
		}
		
		//Atualizar produto
		public Produto update(Produto produto) {
			EntityManager em = JPAUtil.getEntityManager();
			Produto produtoManaged = null;
			
			if(produto.getId() <= 0) {
				throw new DAOException ("O id precisa ser maior que 0", ErrorCode.BAD_REQUEST.getCode());
			}
			if(!produtoIsValid(produto)) {
				throw new DAOException ("Produto com dados incompletos", ErrorCode.BAD_REQUEST.getCode());
				
			}
			
			try {
				em.getTransaction();
				produtoManaged = em.find(Produto.class, produto.getId());
				produtoManaged.setNome(produto.getNome());
				produtoManaged.setQuantidade(produto.getQuantidade());
				em.getTransaction().commit();
			} catch(NullPointerException ex){
				em.getTransaction().rollback();
				throw new DAOException("Produto nao existe" +  ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
			}catch(RuntimeException ex) {
				em.getTransaction().rollback();
				throw new DAOException ("Erro ao atualizar produto. " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
			}
				finally {
					em.close();
			}
			return produtoManaged;
		}
		
		//Deletar produto
		public Produto delete(long id) {
			EntityManager em = JPAUtil.getEntityManager();
			Produto produto = null;
			
			if(id <= 0) {
				throw new DAOException ("Id do produto deve ser maior que 0.", ErrorCode.BAD_REQUEST.getCode());
			}
			
			try {
				em.getTransaction().begin();
				produto = em.find(Produto.class, id);
				em.remove(produto);
				em.getTransaction().commit();
				
			}catch(IllegalArgumentException ex) {
				throw new DAOException ("Id do produto informado nao existe." + ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
			}catch(RuntimeException ex) {
				em.getTransaction().rollback();
				throw new DAOException ("Erro ao deletar produto" + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
			}
			
			finally {
				em.close();
			}
			return produto;
		}
		
		 private boolean produtoIsValid(Produto produto) {
		      try {
		      if ((produto.getNome().isEmpty()) || (produto.getQuantidade() < 0))
		            return false;
		      } catch (NullPointerException ex) {
		      throw new DAOException("Produto com dados incompletos.",
		        ErrorCode.BAD_REQUEST.getCode());
		      }

		       return true;
		   }
}
