<template>
  <div class="contenedor">
    <div class="container mt-5">
      <h1 class="titulo text-center mb-4">📚 Gestión de Libros</h1>

      <!-- Formulario para agregar libros -->
      <div class="card p-4 mb-4 formulario shadow">
        <h5 class="mb-3 text-center subtitulo">Agregar nuevo libro</h5>
        <form @submit.prevent="agregarLibro">
          <div class="row g-2">
            <div class="col-md-6">
              <input v-model="nuevoLibro.titulo" type="text" class="form-control" placeholder="Título" required />
            </div>
            <div class="col-md-6">
              <input v-model="nuevoLibro.autor" type="text" class="form-control" placeholder="Autor" required />
            </div>
            <div class="col-md-6">
              <input v-model="nuevoLibro.categoria" type="text" class="form-control" placeholder="Categoría" required />
            </div>
            <div class="col-md-6">
              <input v-model="nuevoLibro.descripcion" type="text" class="form-control" placeholder="Descripción" />
            </div>
            <div class="col-md-12">
              <input v-model="nuevoLibro.imagen" type="text" class="form-control" placeholder="URL de Imagen (opcional)" />
            </div>
          </div>
          <div class="text-end mt-3">
            <button type="submit" class="btn btn-rosa">📥 Guardar</button>
          </div>
        </form>
      </div>

      <!-- Tabla de libros -->
      <div class="card p-4 shadow tabla-contenedor">
        <h5 class="text-center subtitulo mb-3">Lista de Libros</h5>
        <table class="table tabla-libros text-center">
          <thead>
            <tr>
              <th>ID</th>
              <th>Imagen</th>
              <th>Título</th>
              <th>Autor</th>
              <th>Categoría</th>
               <th style="width: 20%;">Descripción</th> <!-- Más angosto -->
    <th style="width: 18%;">Acciones</th>    <!-- Más espacio para botones -->
            </tr>
          </thead>
          <tbody>
            <tr v-for="libro in libros" :key="libro.id">
              <td>{{ libro.id }}</td>
              <td>
                <img :src="libro.imagen || 'https://via.placeholder.com/60'" width="60" height="60" />
              </td>
              <td>{{ libro.titulo }}</td>
              <td>{{ libro.autor }}</td>
              <td>{{ libro.categoria }}</td>
              <td>{{ libro.descripcion }}</td>
              <td>
                <button class="btn btn-sm btn-eliminar" @click="eliminarLibro(libro.id)">🗑</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      libros: [],
      nuevoLibro: {
        titulo: '',
        autor: '',
        categoria: '',
        descripcion: '',
        imagen: ''
      },
      urlBase: 'http://localhost:8080/libros'
    };
  },
  methods: {
    obtenerLibros() {
      fetch(`${this.urlBase}/traer-libros`)
        .then(res => res.json())
        .then(data => {
          this.libros = data;
        })
        .catch(err => console.error("Error al obtener libros:", err));
    },
    agregarLibro() {
      fetch(`${this.urlBase}/insertar-libros`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.nuevoLibro)
      })
        .then(res => res.json())
        .then(() => {
          this.obtenerLibros();
          this.nuevoLibro = { titulo: '', autor: '', categoria: '', descripcion: '', imagen: '' };
        })
        .catch(err => console.error("Error al guardar libro:", err));
    },
    eliminarLibro(id) {
      if (confirm("¿Estás segura de que deseas eliminar este libro?")) {
        fetch(`${this.urlBase}/eliminar-libro/${id}`, {
          method: 'DELETE'
        })
          .then(res => {
            if (!res.ok) throw new Error("No se pudo eliminar el libro.");
            this.obtenerLibros();
          })
          .catch(err => console.error("Error al eliminar libro:", err));
      }
    }
  },
  mounted() {
    this.obtenerLibros();
  }
};
</script>

<style scoped>
/* Fondo difuminado */
.contenedor {
  background: linear-gradient(to right top, #ffcbda, #f6f8a3, #ffaff2);
  min-height: 100vh;
  padding: 2rem 0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Título principal */
.titulo {
  font-size: 3rem;
  font-weight: bold;
  color: #f63b86;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.751);
}

/* Subtítulos */
.subtitulo {
  font-size: 1.3rem;
  color: #444;
}

/* Botón rosa personalizado */
.btn-rosa {
  background-color: #fc5e9d;
  color: white;
  border: none;
  transition: 0.3s ease;
}

.btn-rosa:hover {
  background-color: #ad1457;
}

/* Formulario y tarjetas */
.formulario,
.card {
  background-color: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  border: none;
}

/* Tabla personalizada */
.tabla-libros {
  border-collapse: separate;
  border-spacing: 0;
  width: 100%;
  background-color: #ffc3ea;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.352);
}

.tabla-libros thead tr {
  background-color: #fdafca;
  color: #4a4a4a;
}

.tabla-libros thead th {
  padding: 12px;
  font-weight: bold;
  border-bottom: 2px solid #e0e0e0;
}

.tabla-libros tbody td {
  padding: 12px;
  vertical-align: middle;
  border-top: 1px solid #f0f0f0;
}

.tabla-libros tbody tr:hover {
  background-color: #dc30a6;
}

.tabla-libros img {
  border-radius: 8px;
  object-fit: cover;
}

.btn-eliminar {
  background-color: #dd231f;
  color: white;
  padding: 6px 15px;
  border-radius: 12px;
  font-size: 0.8rem;
  border: none;
  transition: background-color 0.3s ease;
}

.btn-eliminar:hover {
  background-color: #c62828;
}

</style>
