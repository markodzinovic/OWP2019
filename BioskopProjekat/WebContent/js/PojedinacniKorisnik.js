$(document).ready(function() {
	
	var korisnickoIme = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(korisnickoIme);
	
	function jedanKorisnik () {
		
		var korisni = $('#korisnickoImeP');
		var datum = $('#datumP');
		
		var param = {
			'korisnickoIme' : korisnickoIme
		}
		
		$.get('PojedinacniKorisnikServlet', param , function(data) {
			console.log(data)
			
			var kor = data.korisnik;
			
			korisni.val(kor.korisnickoIme);
			datum.val(kor.datumRegistracije);
			console.log(kor.uloga)
			
			var uloga = document.getElementById("ulogaB");
			
			uloga.value = kor.uloga;
			
		})
		
	}
	
	$('#izmenaKorisnika').on('click', function() {
		var k = $('#korisnickoImeP');
		var datumR = $('#datumP');
		var u = document.getElementById("ulogaB");
		
		var korisnickoIme = k.val()
		var datumRegistracije = datumR.val()
		var uloga = u.value
		
		
		console.log(korisnickoIme)
		console.log(datumRegistracije)
		console.log(uloga)
		
		 params = {
			'akcija' : 'izmena',
			'korisnickoIme' : korisnickoIme,
			'datumRegistracije' : datumRegistracije,
			'uloga' : uloga
		}
		
		$.post('PojedinacniKorisnikServlet', params, function(data) {
			
			if (data.status == 'success') {
				alert("Izmenjen");
			}
		
			
		})
		
		
	})
	
	$('#brisanjeKorisnika').on('click', function() {
		var k = $('#korisnickoImeP');
	
		var korisnickoIme = k.val()
		
		param = {
			'akcija' : 'brisanje',
			'korisnickoIme' : korisnickoIme
		}
		$.post('PojedinacniKorisnikServlet', param , function(data) {
			
			if (data.status == 'success') {
				alert("Obrisan");
			}
		} )
		
	} )
	
	jedanKorisnik();
})