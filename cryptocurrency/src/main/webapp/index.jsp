<html>
	<head>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
		<style>
			tbody td {
				padding : 5px;
			}
		</style>		 
	</head>
	<body>
		<table class="crypto-currency-lookup-table"  rules="all" border="2px solid">			
		</table>
		<script>
			$( document ).ready(function() {
				$.ajax({
					url: "bestProfitCaluculator",
					dataType: 'json',
					success: function(result){
						console.log(result);
						var size = result.length;
						var tableHead = '';
						var tableSubHead = '';
						var priceInfo = '';
						var timeInfo = '';
						var profitInfo = '';
						for(var i = 0 ; i < size ; i++){
							tableHead = tableHead + '<th>'+result[i].currency+'</th><th></th>';
							tableSubHead = tableSubHead + '<td>Buy</td><td>Sell</td>';
							var buyPrice = parseFloat(result[i].buyPrice);
							var sellPrice = parseFloat(result[i].sellPrice);
							priceInfo = priceInfo + '<td>'+ buyPrice + '</td><td>' + sellPrice + '</td>';
							timeInfo = timeInfo + '<td>'+result[i].buyTime + '</td><td>' + result[i].sellTime + '</td>';
							profitInfo = profitInfo + '<td> Profit </td><td>' + (sellPrice - buyPrice).toFixed(2) + '</td>';
						}
						tableHead = '<thead><tr>' + tableHead + ' </tr></thead>';
						tableSubHead = '<tr>'+ tableSubHead +'</tr>';
						priceInfo = '<tr>'+ priceInfo +'</tr>';
						timeInfo = '<tr>'+ timeInfo +'</tr>';
						profitInfo = '<tr>'+ profitInfo +'</tr>';
						$('.crypto-currency-lookup-table').html(tableHead + '<tbody>' + tableSubHead + priceInfo + timeInfo + profitInfo +'</tbody>');
					}				    
				});
			});
		</script>
	</body>
</html>
