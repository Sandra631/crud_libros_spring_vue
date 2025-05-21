<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">📚 Gestión de Libros</h2>

    <!-- Formulario para agregar libros -->
    <div class="card p-4 mb-4">
      <h5 class="mb-3 text-center">Agregar nuevo libro</h5>
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
          <button type="submit" class="btn btn-success">📥 Guardar</button>
        </div>
      </form>
    </div>

    <!-- Tabla de libros -->
    <div class="card p-4">
      <h5 class="text-center mb-3">Lista de Libros</h5>
      <table class="table table-bordered text-center">
        <thead class="table-light">
          <tr>
            <th>Imagen</th>
            <th>Título</th>
            <th>Autor</th>
            <th>Categoría</th>
            <th>Descripción</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="libro in libros" :key="libro.id">
            <td>
              <img :src="libro.imagen" width="60" height="60" style="object-fit: cover; border-radius: 50%;" />
            </td>
            <td>{{ libro.titulo }}</td>
            <td>{{ libro.autor }}</td>
            <td>{{ libro.categoria }}</td>
            <td>{{ libro.descripcion }}</td>
          </tr>
        </tbody>
      </table>
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
          this.obtenerLibros(); // Recargar la lista
          this.nuevoLibro = { titulo: '', autor: '', categoria: '', descripcion: '', imagen: '' };
        })
        .catch(err => console.error("Error al guardar libro:", err));
    }
  },
  mounted() {
    this.obtenerLibros();
  }
};
</script>

<style scoped>
table img {
  border-radius: 8px;
}
</style>
