$(document).ready(function() {
	
	var tabelaFilmovi = $('#filmoviTabela');
	

	

		
		$.get('FilmoviServlet', function(data) {
			
			var filmovi = data.sviFilmovi;
			
			for ( f in filmovi) {
				tabelaFilmovi.append('<tr>'+
						'<td>' + filmovi[f].naziv + '</td>' + 
						'<td>' + filmovi[f].zanrovi + '</td>' +
						'<td>' + filmovi[f].trajanje + 'min'  + '</td>' +
						'<td>' + filmovi[f].distributer + '</td>' +
						'<td>' + filmovi[f].zemljaPorekla + '</td>' +
						'<td>' + filmovi[f].godinaProizvodnje + '</td>' +
				'</tr>')
			}
			
		})
})