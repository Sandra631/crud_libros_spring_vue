<template>
  <div class="contenedor">
    <div class="container mt-5">
      <h1 class="titulo text-center mb-4">📚 Gestión de Libros</h1>

      <!-- Mensaje de éxito -->
      <div v-if="mensajeExito" class="alert alert-success text-center">
        {{ mensajeExito }}
      </div>

      <!-- Formulario para agregar o editar libros -->
      <div class="card p-4 mb-4 formulario shadow">
        <h5 class="mb-3 text-center subtitulo">
          {{ editandoId !== null ? 'Editar libro' : 'Agregar nuevo libro' }}
        </h5>
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
            <button type="submit" class="btn btn-rosa">
              {{ editandoId !== null ? '💾 Actualizar' : '📥 Guardar' }}
            </button>
            <button v-if="editandoId !== null" type="button" class="btn btn-secondary ms-2" @click="limpiarFormulario">❌ Cancelar</button>
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
              <th style="width: 20%;">Descripción</th>
              <th style="width: 18%;">Acciones</th>
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
                <button class="btn btn-editar btn-sm me-2" @click="editarLibro(libro)">✏️</button>
                <button class="btn btn-eliminar btn-sm" @click="eliminarLibro(libro.id)">🗑️</button>

                
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>



<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';

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
      editandoId: null,
      mensajeExito: '',
      urlBase: import.meta.env.VITE_API_URL + '/libros'
    };
  },
  methods: {
    obtenerLibros() {
      axios.get(`${this.urlBase}/traer-libros`)
        .then(res => {
          this.libros = res.data;
        })
        .catch(err => console.error("Error al obtener libros:", err));
    },
    agregarLibro() {
  if (this.editandoId !== null) {
    this.actualizarLibro();
    return;
  }

  axios.post(`${this.urlBase}/insertar-libros`, this.nuevoLibro)
    .then(() => {
      this.obtenerLibros();
      this.limpiarFormulario();
      Swal.fire({
        title: "Libro agregado exitosamente.",
        icon: "success",
        allowOutsideClick: true,  // Opcional: permite cerrarlo al hacer clic afuera
        allowEscapeKey: true,     // Opcional: permite cerrarlo con ESC
        position: 'bottom-center',   // Opcional: posicionar como un toast
        timer: 3000,
        showConfirmButton: false
      });
    })
},
    eliminarLibro(id) {
      Swal.fire({
        title: "¿Estás segura de que deseas eliminar este libro?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sí, eliminarlo",
        cancelButtonText: "Cancelar"
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(`${this.urlBase}/eliminar-libro/${id}`)
            .then(() => {
              this.obtenerLibros();
              Swal.fire({
            title: "🗑️ Eliminado",
            text: "El libro ha sido eliminado exitosamente.",
            icon: "success",
            timer: 2000,
            showConfirmButton: false
             });
        })
        }
      });
    },

    editarLibro(libro) {
      this.nuevoLibro = { ...libro };
      this.editandoId = libro.id;
    },
    actualizarLibro() {
  Swal.fire({
    title: "¿Deseas guardar los cambios?",
    showDenyButton: true,
    showCancelButton: true,
    confirmButtonText: "Guardar",
    denyButtonText: "No guardar"
  }).then((result) => {
    if (result.isConfirmed) {
      axios.put(`${this.urlBase}/editar-libro/${this.editandoId}`, this.nuevoLibro)
        .then(() => {
          this.obtenerLibros();
          this.limpiarFormulario();
          Swal.fire("Cambios guardados", "", "success");
        })
        .catch(err => {
          console.error("Error al actualizar libro:", err);
          Swal.fire("Error al guardar los cambios", "", "error");
        });
    } else if (result.isDenied) {
      Swal.fire("Los cambios no se guardaron", "", "info");
    }
  });
},
limpiarFormulario() {
      this.nuevoLibro = { titulo: '', autor: '', categoria: '', descripcion: '', imagen: '' };
      this.editandoId = null;
    }
  },
  mounted() {
    this.obtenerLibros();
  }
};
</script>


<style scoped>
.contenedor {
  background: linear-gradient(to right top, #ffcbda, #f6f8a3, #ffaff2);
  min-height: 100vh;
  padding: 2rem 0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.titulo {
  font-size: 3rem;
  font-weight: bold;
  color: #f63b86;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.751);
}

.subtitulo {
  font-size: 1.3rem;
  color: #444;
}

.btn-rosa {
  background-color: #fc5e9d;
  color: white;
  border: none;
  transition: 0.3s ease;
}

.btn-rosa:hover {
  background-color: #ad1457;
}

.btn-eliminar {
  background-color: #dd231f;
  color: white;
  padding: 5px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
  border: none;
  transition: background-color 0.3s ease;
}

.btn-eliminar:hover {
  background-color: #c62828;
}

.btn-editar {
  background-color: #f6e764;
  color: white;
  padding: 5px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
  border: none;
  transition: background-color 0.3s ease;
}

.btn-editar:hover {
  background-color: #ad1457;
}

.formulario,
.card {
  background-color: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  border: none;
}

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


</style>
