Given(/^I have variable a$/) do
  @a = 50
end

And(/^I have variable b$/) do
  @b = 70
end

When(/^I multiplication a and b$/) do
  @mul = @a * @b
end

Then(/^I display the Result$/) do
  puts "Multiplication of #{@a} and #{@b} is #{@mul}"
end