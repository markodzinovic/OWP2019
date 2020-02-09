$(document).ready(function() {
	
	var tabelaProjekcije = $('#projekcijaTabela');
	var tabelaFilmovi = $('#filmoviTabela');
	var naslov = $('#film');
	
	naslov.hide();
	tabelaFilmovi.hide();
	
	$.get('PocetnaStranicaServlet', function(data) {
		
		var projekcije = data.sveProjekcije;
		for ( i in projekcije) {
			tabelaProjekcije.append('<tr>'+
					'<td>' + projekcije[i].nazivFilma.naziv + '</td>'+
					'<td>' + projekcije[i].datum + '</td>' +
					'<td>' + projekcije[i].tipProjekcije.naziv + '</td>' +
					'<td>' + projekcije[i].sala.naziv + '</td>' +
					'<td>' + projekcije[i].cena + 'e'  + '</td>' +
			'</tr>')
		}
		
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
		
		$('#pokazi').on('click', function() {
			naslov.show();
			tabelaFilmovi.show();
		})
		
		$('#sakrij').on('click', function() {
			naslov.hide();
			tabelaFilmovi.hide();
		})
		
		
	})
	
})