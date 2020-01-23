$(document).ready(function() {
	
	var nazivFilma = $('#nazivFilma');
	var reziserFilm = $('#reziserFilm');
	var glumciFilm = $('#glumciFilm');
	var zanroviFilm = $('#zanroviFilm');
	var trajanjeFilm = $('#trajanjeFilm');
	var distributerFilm = $('#distributerFilm');
	var zemljaFilm = $('#zemljaFilm');
	var godinaFilm = $('#godinaFilm');
	var opisFilm = $('#opisFilm');
	
	var porukaParagraf = $('#porukaParagraf');
	
	$('#dodajFilm').on('click', function () {
		
		var naziv = nazivFilma.val();
		var reziser = reziserFilm.val();
		var glumci = glumciFilm.val();
		var zanrovi = zanroviFilm.val();
		var trajanje = trajanjeFilm.val();
		var distributer = distributerFilm.val();
		var zemlja = zemljaFilm.val();
		var godina = godinaFilm.val();
		var opis = opisFilm.val();
		
		params = {
				'akcija' : 'dodavanje',
				'nazivD' : naziv,
				'reziserD' : reziser,
				'glumciD' : glumci,
				'zanroviD' : zanrovi,
				'trajanjeD' : trajanje,
				'distributerD' : distributer,
				'zemljaD' : zemlja,
				'godinaD' : godina,
				'opisD' : opis
		}
		$.post('PojedinacniFilmServlet', params , function(data) {
			console.log(data)
			
			if(data.status == 'failure'){
				porukaParagraf.text(data.poruka);
				return;
			}
			if(data.status == 'success'){
				window.location.replace('Filmovi.html');
				return;
			}
			
		})
	
	
	})
	
	
	
	
})