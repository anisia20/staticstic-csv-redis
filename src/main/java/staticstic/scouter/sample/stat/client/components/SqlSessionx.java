package staticstic.scouter.sample.stat.client.components;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@Data
public class SqlSessionx {
	
	@Resource
    @Qualifier("apiSqlSessionTemplate")
    private SqlSession sqlSession;
	
	@Resource
    @Qualifier("apiSqlSessionBatchTemplate")
    private SqlSession sqlBatchSession;
	
	public <T> T selectOne(String statement) {
		return selectOne(statement, null);
	}
	
	public <T> T selectOne(String statement, Object parameter) {
		try {
			T obj = sqlSession.selectOne(statement, parameter);
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", statement, e);
			return null;
		}
	}
	
	public int selectCount(String statement, Object parameter) {
		try {
			int cnt = sqlSession.selectOne(statement, parameter) == null ? -1 : (int) sqlSession.selectOne(statement, parameter);
			return cnt;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", statement, e);
			return -1;
		}
	}
	
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return selectMap(statement, null, mapKey, RowBounds.DEFAULT);
	}
	
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return selectMap(statement, parameter, mapKey, RowBounds.DEFAULT);
	}
	
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		try {
			Map<K, V> obj = sqlSession.selectMap(statement, parameter, mapKey, rowBounds);
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", statement, e);
			return null;
		}
	}
	
	public <E> List<E> selectList(String statement) {
		return selectList(statement, null);
	}
	
	public <E> List<E> selectList(String statement, Object parameter) {
		return selectList(statement, parameter, RowBounds.DEFAULT);
	}
	
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		try {
			List<E> obj = sqlSession.selectList(statement, parameter, rowBounds);
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", statement, e);
			return null;
		}
	}
	
	public int insert(String statement) {
		return insert(statement, null);
	}
	
	public int insert(String statement, Object parameter) {
		return update(statement, parameter);
	}
	
	public int update(String statement) {
		return update(statement, null);
	}
	
	public int update(String statement, Object parameter) {
		try {
			int obj = sqlSession.update(statement, parameter);
			
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", statement, e);
			return -1;
		}
	}
	
	public int delete(String statement) {
		return update(statement, null);
	}
	
	public int delete(String statement, Object parameter) {
		return update(statement, parameter);
	}
	
	public int insBatchQry(String statement, Object parameter) {
		try {
			int obj = sqlBatchSession.insert(statement, parameter);
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}], err={}", statement, e.getMessage(), e);
			return -1;
		}
	}
	
	public int updBatchQry(String statement, Object parameter) {
		try {
			int obj = sqlBatchSession.update(statement, parameter);
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}], err={}", statement, e.getMessage(), e);
			return -1;
		}
	}
	
	
	public int delBatchQry(String statement, Object parameter) {
		try {
			int obj = sqlBatchSession.delete(statement, parameter);
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}], err={}", statement, e.getMessage(), e);
			return -1;
		}
	}
	
	public void commit() {
		commit(false);
	}
	
	public void commit(boolean force) {
		try {
			sqlSession.commit(force);
			
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "commit", e);
		}
	}
	
	public void rollback(boolean force) {
		try {
			sqlSession.rollback(force);
			
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "rollback", e);
		}
	}
	
	public List<BatchResult> flushStatements() {
		try {
			List<BatchResult> obj = sqlSession.flushStatements();
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "flushStatement", e);
			return null;
		}
	}
	
	public List<BatchResult> batchFlushStatements() {
		try {
			List<BatchResult> obj = sqlBatchSession.flushStatements();
			
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}], err={}", "flushStatement", e.getMessage(), e);
			return null;
		}
	}
	
	public void close() {
		try {
			sqlSession.close();
			
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "close", e);
		}
	}
	
	public Connection getConnection() {
		try {
			Connection obj = sqlSession.getConnection();
			
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "getConnection", e);
			return null;
		}
	}

	public void clearCache() {
		try {
			sqlSession.clearCache();
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "clearCache", e);
		}
	}

	public Configuration getConfiguration() {
		try {
			Configuration obj = sqlSession.getConfiguration();
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "getConfiguration", e);
			return null;
		}
	}

	public <T> T getMapper(Class<T> class1) {
		try {
			T obj = sqlSession.getMapper(class1);
			return obj;
		} catch(Exception e) {
			log.error("QRY_ID=[{}]", "getMapper", e);
			return null;
		}
	}
}
