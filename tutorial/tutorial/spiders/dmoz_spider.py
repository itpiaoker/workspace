import scrapy

class DmozSpider(scrapy.Spider):
    name = "dmoz"
    allowed_domains = ["www.sohu.com"]
    start_urls = [
		"http://www.sohu.com/a/218482133_162758?g=0?code=576ccfdbbd6da6c3cbc6504ae679a75c&_f=index_cpc_0"
    ]

    def parse(self, response):
        for sel in response.xpath('//ul/li'):
            title = sel.xpath('a/text()').extract()
            link = sel.xpath('a/@href').extract()
            desc = sel.xpath('text()').extract()
            print (title, link, desc)