$(document).ready(function() {
	
	var idFilma = window.location.search.slice(1).split('&')[0].split('=')[1];
	
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
	var adminKontrola = $('#admin');
	
//	adminKontrola.hide();
	
	function korisnikFilm() {
		
		param = {
				'idFilma' : idFilma
		}
		
		$.get('PojedinacniFilmServlet', param, function(data) {
			
			console.log(data)
			
			var f = data.film;
			
			nazivFilma.val(f.naziv);
			reziserFilm.val(f.reziser);
			glumciFilm.val(f.glumci);
			zanroviFilm.val(f.zanrovi);
			trajanjeFilm.val(f.trajanje);
			distributerFilm.val(f.distributer);
			zemljaFilm.val(f.zemljaPorekla);
			godinaFilm.val(f.godinaProizvodnje);
			opisFilm.val(f.opis);
			

				
				$('#izmeniFilm').on('click', function() {
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
							'idFilma' : idFilma,
							'akcija' : 'izmena',
							'naziv' : naziv,
							'reziser' : reziser,
							'glumci' : glumci,
							'zanrovi' : zanrovi,
							'trajanje' : trajanje,
							'distributer' : distributer,
							'zemlja' : zemlja,
							'godina' : godina,
							'opis' : opis
					}
					
					$.post('PojedinacniFilmServlet', params, function(data) {
						
						console.log(data);
						if(data.status == 'failure'){
							porukaParagraf.text(data.poruka);
							return;
						}
						
						if(data.status == 'success'){
							window.location.replace('Filmovi.html')
							return;
						}
						
					})
					
				})
			
			

			
		})
		
		$('#obrisiFilm').on('click', function() {
			
			params = {
					'idFilma' : idFilma,
					'akcija' : 'brisanje'
			}
			
			$.post('PojedinacniFilmServlet', params, function(data) {
				console.log(data);
				
				if(data == 'success'){
					window.location.replace('Filmovi.html')
					return;
				}
				
			})
		})
		
	}
	
	
	korisnikFilm()
	
/*	$('#odblokiraj').on('click', function() {
		nazivFilma.attr("disabled", false);
		
	})
	*/
})