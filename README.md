### 03-09

# Chunking.

Implementing chunking to split long documents into smaller, more manageable pieces for embedding and
storage in the vector database. 

This version includes an example of how to use a text splitter to divide documents into chunks, 
allowing for more efficient embedding and retrieval in the vector database, especially for large 
documents. 

Chunking can help improve the relevance of search results by ensuring that the embeddings capture 
more focused pieces of information, rather than trying to embed an entire long document into a single vector.


# Prerequisites

- Ollama installed and running
- qwen2.5 model pulled locally
- qdrant server running and accessible

# SETUP
Run VectorStoreInitializer