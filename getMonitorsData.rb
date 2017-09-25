require 'open-uri'
require 'nokogiri'
doc = Nokogiri::HTML(open("https://www.port-monitor.com/plans-and-pricing"))

elements = doc.css('.product')

puts "["
elements.each do |elem|
	# Obtain and show each product data
	puts "  {"
	puts "    monitors: " + elem.css('h2').text
	puts "    check_rate: " + elem.css('dl.thin dd:nth-child(2)').text[6..7]
	puts "    history: " + elem.css('dl.thin dd:nth-child(4)').text[0..1]

	multiple_notifications = "false";
	if( elem.css('dd:nth-child(6) span.label').text.eql? "Yes" )
		multiple_notifications = "true";
	end
	puts "    multiple_notifications: " + multiple_notifications

	push_notifications = "false";
	if( elem.css('dd:nth-child(8) span.label').text.eql? "Yes" )
		push_notifications = "true";
	end
	puts "    push_notifications: " + push_notifications

	price = elem.css('p a.btn').text
	puts "    price: " + price[1, price.length - 5]
	puts "  }"
end
