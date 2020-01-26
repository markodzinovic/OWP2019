$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var tabelaFilmovi = $('#filmoviTabela');
	var adminParagraf = $('#adminParagraf');
	adminParagraf.hide();
		
		$.get('FilmoviServlet', function(data) {
			
			if(data.status =='unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				
				var filmovi = data.sviFilmovi;
				if(data.uloga == 'ADMIN'){
					adminParagraf.show();
				}
				
				for ( f in filmovi) {
					tabelaFilmovi.append('<tr>'+
							'<td><a href="PojedinacniFilm.html?id='+filmovi[f].id+'"  >' + filmovi[f].naziv + '</td>' + 
							'<td>' + filmovi[f].zanrovi + '</td>' +
							'<td>' + filmovi[f].trajanje + 'min'  + '</td>' +
							'<td>' + filmovi[f].distributer + '</td>' +
							'<td>' + filmovi[f].zemljaPorekla + '</td>' +
							'<td>' + filmovi[f].godinaProizvodnje + '</td>' +
					'</tr>')
				}
				
			}
			

			
		})
})